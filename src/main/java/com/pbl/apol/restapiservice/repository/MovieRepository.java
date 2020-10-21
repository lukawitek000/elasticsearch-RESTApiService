package com.pbl.apol.restapiservice.repository;

import com.pbl.apol.restapiservice.model.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends ElasticsearchRepository<Movie, String> {
    @Override
    List<Movie> findAll();


    List<Movie> findMovieByName(String title);


}
