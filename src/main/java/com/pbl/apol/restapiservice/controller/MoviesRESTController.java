package com.pbl.apol.restapiservice.controller;


import com.pbl.apol.restapiservice.model.Movie;
import com.pbl.apol.restapiservice.model.Person;
import com.pbl.apol.restapiservice.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pbl/movies")
public class MoviesRESTController {

    private MovieServiceImpl movieService;

    @Autowired
    public MoviesRESTController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }


    @RequestMapping("/getall")
    public List<Movie> getAll(){
        return movieService.getAll();
    }


    @GetMapping(value = "/findbytitle")
    public List<Movie> getByTitle(@RequestParam("title") String title, @RequestParam(value = "size", defaultValue = "3") int size){
        return trimList(movieService.getByTitle(title), size);
        //return movieService.getByTitle(title);
    }

    @GetMapping(value = "/search")
    public List<Movie> searchByGivenField(@RequestParam("field") String field, @RequestParam("value") String value,
                                          @RequestParam(value = "size", defaultValue = "3") int size,
                                          @RequestParam(value = "minScore", defaultValue = "0") float minScore){
        List<Movie> moviesList = movieService.searchByGivenField(field, value);
        return trimList(filterList(moviesList, minScore), size);
        //return trimList(movieService.searchByGivenField(field, value), size);
        //return movieService.searchByGivenField(field, value);
    }

    private List<Movie> filterList(List<Movie> moviesList, float minScore) {
        CollectionUtils.filter(moviesList, p -> ((Movie) p).getScore() > minScore);
        return moviesList;
    }

    @GetMapping
    public List<Movie> search(@RequestParam("value") String value, @RequestParam(value = "size", defaultValue = "3") int size,
                              @RequestParam(value = "minScore", defaultValue = "0") float minScore){
        return trimList(filterList(movieService.search(value), minScore), size);
        //return movieService.search(value);
    }

    private List<Movie> trimList(List<Movie> moviesList, int size) {
        if(moviesList.size() > size){
            moviesList = moviesList.subList(0, size);
        }
        return moviesList;
    }



}
