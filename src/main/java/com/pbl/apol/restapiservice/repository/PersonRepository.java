package com.pbl.apol.restapiservice.repository;

import com.pbl.apol.restapiservice.model.Person;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface PersonRepository extends ElasticsearchRepository<Person, String> {
   // ElasticsearchCrudRepository
    List<Person> findPersonByName(String name);

    @Override
    List<Person> findAll();


    @Override
    List<Person> search(QueryBuilder queryBuilder);
}
