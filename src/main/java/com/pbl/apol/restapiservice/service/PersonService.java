package com.pbl.apol.restapiservice.service;

import com.pbl.apol.restapiservice.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface PersonService {
    List<Person> findAllPeople();
    List<Person> findByName(String name);
   // List<Person> fieldContains(String field, String value);
    //List<Person> dismaxSearch(String value, int start, int size, float minScore);
    List<Person> searchByGivenField(String field, String value);
    List<Person> search(String value);
}

