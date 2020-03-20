package com.formacao.demo.controller;

import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;
import com.formacao.demo.service.OMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/movies")
public class OMDBController {
    private OMDBService omdbService;

    @Autowired
    public OMDBController(OMDBService omdbService) {
        this.omdbService = omdbService;
    }

    @GetMapping(value = "{/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OMDB find(@PathVariable String id) {
        return omdbService.find(id);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OMDB> findAll() {
        return omdbService.findAll();
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public OMDB insert(@Valid @RequestBody ClientAndMovieDTO clientAndMovieDTO) {
        return omdbService.locateNewMovieByClient(clientAndMovieDTO);
    }
}
