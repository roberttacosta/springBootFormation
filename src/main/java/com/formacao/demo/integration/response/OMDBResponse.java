package com.formacao.demo.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class OMDBResponse implements Serializable {
    @JsonProperty(value = "Title")
    private String title;
    @JsonProperty(value = "Year")
    private String year;
    @JsonProperty(value = "Genre")
    private String genre;
    @JsonProperty(value = "Director")
    private String director;
    @JsonProperty(value = "Writer")
    private String writer;
    @JsonProperty(value = "Actors")
    private String actors;
    @JsonProperty(value = "Plot")
    private String plot;
    @JsonProperty(value = "Language")
    private String language;
    @JsonProperty(value = "Country")
    private String country;
    @JsonProperty(value = "Poster")
    private String poster;
    @JsonProperty(value = "imdbID")
    private String imdbID;
    @JsonProperty(value = "Type")
    private String type;

    public OMDBResponse(String title, String year, String genre, String director, String writer, String actors, String plot, String language, String country, String poster, String imdbID, String type) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.poster = poster;
        this.imdbID = imdbID;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
