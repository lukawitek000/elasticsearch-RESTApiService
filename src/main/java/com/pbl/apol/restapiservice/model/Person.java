package com.pbl.apol.restapiservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchDateConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Document(indexName = "people")
public class Person {

    @Id
    private String id;

    private String name;

    @Field(name = "_score")
    private float score;


    @JsonProperty("birth date")
    @Field(name = "birth date", type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    private String birthDate;

    @Field("birth place")
    @JsonProperty("birth place")
    private String birthPlace;

    @Field("actor filmography")
    @JsonProperty("actor filmography")
    private String[] actorFilmography;

    @JsonProperty("director filmography")
    private String[] directorFilmography;

    @Field("writer filmography")
    @JsonProperty("writer filmography")
    private String[] writerFilmography;

    @Field("nick names")
    @JsonProperty("nick names")
    private String[] nickNames;

    @Field("birth name")
    @JsonProperty("birth name")
    private String birthName;

    private String height;

    private String biography;

    @Field("trade mark")
    @JsonProperty("trade mark")
    private String[] tradeMarks;

    private String[] quotes;

    private String[] spouse;

    @JsonProperty("death date")
    @Field(name = "death date", type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    private String deathDate;

    @JsonProperty("death place")
    @Field(name = "death place")
    private String deathPlace;

    @JsonProperty("death cause")
    @Field(name = "death cause")
    private String deathCause;

    @JsonProperty("akas")
    @Field(name = "akas")
    private String[] akas;

    private String[] trivia;


    @JsonProperty("salary history")
    @Field(name = "salary history")
    private String[] salaryHistory;

    public String[] getAkas() {
        return akas;
    }

    public void setAkas(String[] akas) {
        this.akas = akas;
    }

    public String[] getTrivia() {
        return trivia;
    }

    public void setTrivia(String[] trivia) {
        this.trivia = trivia;
    }

    public String[] getSalaryHistory() {
        return salaryHistory;
    }

    public void setSalaryHistory(String[] salaryHistory) {
        this.salaryHistory = salaryHistory;
    }



    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String[] getActorFilmography() {
        return actorFilmography;
    }

    public void setActorFilmography(String[] actorFilmography) {
        this.actorFilmography = actorFilmography;
    }

    public String[] getDirectorFilmography() {
        return directorFilmography;
    }

    public void setDirectorFilmography(String[] directorFilmography) {
        this.directorFilmography = directorFilmography;
    }

    public String[] getWriterFilmography() {
        return writerFilmography;
    }

    public void setWriterFilmography(String[] writerFilmography) {
        this.writerFilmography = writerFilmography;
    }

    public String[] getNickNames() {
        return nickNames;
    }

    public void setNickNames(String[] nickNames) {
        this.nickNames = nickNames;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String[] getTradeMarks() {
        return tradeMarks;
    }

    public void setTradeMarks(String[] tradeMarks) {
        this.tradeMarks = tradeMarks;
    }

    public String[] getQuotes() {
        return quotes;
    }

    public void setQuotes(String[] quotes) {
        this.quotes = quotes;
    }

    public String[] getSpouse() {
        return spouse;
    }

    public void setSpouse(String[] spouse) {
        this.spouse = spouse;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

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

    public String getBirthDate() {
        //System.out.println(birthDate);
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getDeathCause() {
        return deathCause;
    }

    public void setDeathCause(String deathCause) {
        this.deathCause = deathCause;
    }
}
