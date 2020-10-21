package com.pbl.apol.restapiservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pbl.apol.restapiservice.model.Person;
import com.pbl.apol.restapiservice.repository.PersonRepository;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class PersonServiceImpl implements PersonService {

   // @Autowired
    private PersonRepository personRepository;


    //@Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, @Qualifier("custom") RestHighLevelClient highLevelClient){
        this.personRepository = personRepository;
        this.highLevelClient = highLevelClient;
    }



    @Override
    public List<Person> findAllPeople() {
        return personRepository.findAll();
    }



    @Override
    public List<Person> findByName(String name) {
        return personRepository.findPersonByName(name);
    }



    @Override
    public List<Person> searchByGivenField(String field, String value){
        SearchRequest searchRequest = new SearchRequest("people");
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        List<Person> people = new ArrayList<>();
        try {
            SearchResponse searchResponse =  highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //System.out.println(searchResponse.getHits().getHits());
            List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
           // System.out.println(searchHits.toString());
            ObjectMapper mapper = new ObjectMapper();
            searchHits.forEach(hit -> {
                try {
                    people.add(mapper.readValue(hit.getSourceAsString(), Person.class));
                    people.get(people.size() - 1).setScore(hit.getScore());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
           // System.out.println(persons.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return people;
       //return null;

/*

        QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
        return personRepository.search(queryBuilder);

 */
    }

    @Override
    public List<Person> search(String value) {

        System.out.println("elooooo " + value);
        QueryBuilder multi = QueryBuilders.multiMatchQuery(value);


        SearchRequest searchRequest = new SearchRequest().indices("people");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(multi);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            /*
            System.out.println(searchResponse.getHits().getHits().length);

            for(int i =0; i<searchResponse.getHits().getHits().length; i++){
                System.out.println(searchResponse.getHits().getHits()[i].getSourceAsString());
            }
            */


            List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            List<Person> elasticsearchPeople = new ArrayList<>();
            searchHits.forEach(hit -> {
                try {
                    elasticsearchPeople.add(mapper.readValue(hit.getSourceAsString(), new TypeReference<Person>() {
                    }));
                    elasticsearchPeople.get(elasticsearchPeople.size() - 1).setScore(hit.getScore());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return elasticsearchPeople;
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;

    }


    private QueryBuilder getQueryBuilder(String value) {

        value = value.toLowerCase();

        return QueryBuilders.disMaxQuery()
                .add(matchPhraseQuery("post_title", value).boost(3))
                .add(matchPhraseQuery("post_excerpt", value).boost(3))
                .add(matchPhraseQuery("terms.post_tag.name", value).boost(2))
                .add(matchPhraseQuery("terms.category.name", value).boost(2))
                .add(matchPhraseQuery("post_content", value).boost(1));
    }

}
