package com.pbl.apol.restapiservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl.apol.restapiservice.model.Movie;
import com.pbl.apol.restapiservice.model.Person;
import com.pbl.apol.restapiservice.repository.MovieRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{
    private MovieRepository movieRepository;
    private RestHighLevelClient highLevelClient;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, @Qualifier("custom") RestHighLevelClient highLevelClient){
        this.movieRepository = movieRepository;
        this.highLevelClient = highLevelClient;
    }


    public List<Movie> getAll(){
       return movieRepository.findAll();
    }

    public List<Movie> getByTitle(String title) {
        return movieRepository.findMovieByName(title);
    }

    //@Override
    public List<Movie> searchByGivenField(String field, String value){
        SearchRequest searchRequest = new SearchRequest("movies");
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        List<Movie> movies = new ArrayList<>();
        try {
            SearchResponse searchResponse =  highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //System.out.println(searchResponse.getHits().getHits());
            List<SearchHit> searchHits = Arrays.asList(searchResponse.getHits().getHits());
            // System.out.println(searchHits.toString());
            ObjectMapper mapper = new ObjectMapper();
            searchHits.forEach(hit -> {
                try {
                    movies.add(mapper.readValue(hit.getSourceAsString(), Movie.class));
                    movies.get(movies.size() - 1).setScore(hit.getScore());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            });
             System.out.println(movies.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
        //return null;

/*

        QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
        return personRepository.search(queryBuilder);

 */
    }

   // @Override
    public List<Movie> search(String value) {

        System.out.println("elooooo " + value);
        QueryBuilder multi = QueryBuilders.multiMatchQuery(value);


        SearchRequest searchRequest = new SearchRequest().indices("movies");
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
            List<Movie> movies = new ArrayList<>();
            searchHits.forEach(hit -> {
                try {
                    movies.add(mapper.readValue(hit.getSourceAsString(), Movie.class));
                    movies.get(movies.size() - 1).setScore(hit.getScore());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return movies;
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;

    }


}
