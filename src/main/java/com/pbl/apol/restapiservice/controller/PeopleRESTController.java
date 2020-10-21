package com.pbl.apol.restapiservice.controller;


import com.pbl.apol.restapiservice.model.Movie;
import com.pbl.apol.restapiservice.model.Person;
import com.pbl.apol.restapiservice.service.PersonService;
import com.pbl.apol.restapiservice.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pbl/people")
public class PeopleRESTController {

   // @Autowired
    private PersonService personService;
    @Autowired
    public PeopleRESTController(PersonService personService){
        this.personService = personService;
    }


    @GetMapping(value = "/getall")
    public List<Person> getAllPeople(){
        return personService.findAllPeople();
    }

    @GetMapping(value = "/findbyname")
    public List<Person> getByName(@RequestParam("name") String name, @RequestParam(value = "size", defaultValue = "3") int size){
       /* List<Person> personList = personService.findByName(name);
        if(personList.size() > size){
            personList = personList.subList(0, size);
        }
        return personList;*/
        return trimList(personService.findByName(name), size);
    }

    private List<Person> trimList(List<Person> personList, int size) {
        if(personList.size() > size){
            personList = personList.subList(0, size);
        }
        return personList;
    }


    @GetMapping(value = "/search")
    public List<Person> searchByGivenField(@RequestParam("field") String field, @RequestParam("value") String value,
                                           @RequestParam(value = "size", defaultValue = "3") int size,
                                           @RequestParam(value = "minScore", defaultValue = "0") float minScore){
        return trimList(filterList(personService.searchByGivenField(field, value), minScore), size);
        //return personService.searchByGivenField(field, value);
    }

    @GetMapping
    public List<Person> search(@RequestParam("value") String value, @RequestParam(value = "size", defaultValue = "3") int size,
                               @RequestParam(value = "minScore", defaultValue = "0") float minScore){
        return trimList(filterList(personService.search(value), minScore), size);
        //return personService.search(value);
    }

    private List<Person> filterList(List<Person> peopleList, float minScore) {
        CollectionUtils.filter(peopleList, p -> ((Person) p).getScore() > minScore);
        return peopleList;
    }


}
