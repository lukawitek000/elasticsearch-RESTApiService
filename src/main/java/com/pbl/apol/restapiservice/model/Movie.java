package com.pbl.apol.restapiservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "movies")
public class Movie {

    @Id
    private String id;

    @JsonProperty("Name")
    @Field("Name")
    private String name;

    @Field(name = "_score")
    private float score;

    @JsonProperty("Directors")
    @Field("Directors")
    private String[] directors;

    @JsonProperty("Writers")
    @Field("Writers")
    private String[] writers;

    @JsonProperty("Genres")
    @Field("Genres")
    private String[] genres;

    @JsonProperty("Year")
    @Field("Year")
    private int year;

    @JsonProperty("Rating")
    @Field("Rating")
    private float rating;

    @JsonProperty("Cast")
    @Field("Cast")
    private String[] cast;

    @JsonProperty("Producers")
    @Field("Producers")
    private String[] producesrs;

    @JsonProperty("Production companies")
    @Field("Production companies")
    private String[] productionCompanies;

    @JsonProperty("Plot outline")
    @Field("Plot outline")
    private String plotOutline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String[] getWriters() {
        return writers;
    }

    public void setWriters(String[] writers) {
        this.writers = writers;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String[] getProducesrs() {
        return producesrs;
    }

    public void setProducesrs(String[] producesrs) {
        this.producesrs = producesrs;
    }

    public String[] getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(String[] productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public String getPlotOutline() {
        return plotOutline;
    }

    public void setPlotOutline(String plotOutline) {
        this.plotOutline = plotOutline;
    }
}
