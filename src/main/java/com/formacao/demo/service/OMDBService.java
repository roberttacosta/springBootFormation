package com.formacao.demo.service;

import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;

import java.util.List;

public interface OMDBService {

    OMDB locateNewMovieByClient(ClientAndMovieDTO clientAndMovieDTO);

    OMDB find(String title);

    List<OMDB> findAll();
}
