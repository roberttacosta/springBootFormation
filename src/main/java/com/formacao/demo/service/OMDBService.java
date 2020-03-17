package com.formacao.demo.service;

import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;

import java.util.List;

public interface OMDBService {

    OMDB LocateNewMovieByClient(ClientAndMovieDTO clientAndMovieDTO);

    OMDB find(String id);

    List<OMDB> findAll();
}
