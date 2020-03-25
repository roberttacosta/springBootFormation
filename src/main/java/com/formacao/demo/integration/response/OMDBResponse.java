package com.formacao.demo.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OMDBResponse implements Serializable {
    @JsonProperty(value = "Title")
    private String Title;
    @JsonProperty(value = "Year")
    private String Year;
    @JsonProperty(value = "Genre")
    private String Genre;
    @JsonProperty(value = "Director")
    private String Director;
    @JsonProperty(value = "Writer")
    private String Writer;
    @JsonProperty(value = "Actors")
    private String Actors;
    @JsonProperty(value = "Plot")
    private String Plot;
    @JsonProperty(value = "Language")
    private String Language;
    @JsonProperty(value = "Country")
    private String Country;
    @JsonProperty(value = "Poster")
    private String Poster;
    @JsonProperty(value = "imdbID")
    private String ImdbID;
    @JsonProperty(value = "Type")
    private String Type;

    public OMDBResponse() {

    }

    public OMDBResponse(String title, String year, String genre, String director, String writer, String actors, String plot, String language, String country, String poster, String imdbID, String type) {
        Title = title;
        Year = year;
        Genre = genre;
        Director = director;
        Writer = writer;
        Actors = actors;
        Plot = plot;
        Language = language;
        Country = country;
        Poster = poster;
        ImdbID = imdbID;
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getImdbID() {
        return ImdbID;
    }

    public void setImdbID(String imdbID) {
        ImdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}

