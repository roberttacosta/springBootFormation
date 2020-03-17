package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;
import com.formacao.demo.integration.configuration.OMDBApi;
import com.formacao.demo.integration.response.OMDBResponse;
import com.formacao.demo.repository.OMDBRepository;
import com.formacao.demo.service.ClientService;
import com.formacao.demo.service.OMDBService;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OMDBServiceImpl implements OMDBService {

    private OMDBRepository omdbRepository;
    private ClientService clientService;
    private OMDBApi omdbApi;

    @Autowired
    public OMDBServiceImpl(OMDBRepository omdbRepository, ClientService clientService, OMDBApi omdbApi) {
        this.omdbRepository = omdbRepository;
        this.clientService = clientService;
        this.omdbApi = omdbApi;
    }

    @Override
    public OMDB find(String id) {
        return omdbRepository.findById(id).orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + OMDB.class.getName()));
    }

    @Override
    public List<OMDB> findAll(){
        return omdbRepository.findAll();
    }

    @Override
    public OMDB LocateNewMovieByClient(ClientAndMovieDTO clientAndMovieDTO) {
        Client client = clientService.findByCPF(clientAndMovieDTO.getCpf());
        OMDB omdb = builOmdb(omdbApi.findByName(clientAndMovieDTO.getTitle(), "a549d02f"));


        checkIfInNotSameMovie(client, omdb);
        checkIfAccountIsNotNegative(client, omdb);

        client.getAccount().setBalance((client.getAccount().getBalance() - omdb.getValue()));

        client.getOmdbs().add(omdb);
        omdb.getClients().add(client);

       return omdbRepository.save(omdb);
    }

    private OMDB builOmdb(OMDBResponse omdbResponse){
        Random random = new Random();
        return new OMDB(omdbResponse.getTitle(), omdbResponse.getYear(), omdbResponse.getGenre(), omdbResponse.getDirector(), omdbResponse.getWriter(), omdbResponse.getActors(), omdbResponse.getPlot(), omdbResponse.getLanguage(), omdbResponse.getCountry(), omdbResponse.getPoster(), omdbResponse.getImdbID(), omdbResponse.getType(), (random.nextDouble() * 100));
    }

    private void checkIfAccountIsNotNegative(Client client, OMDB omdb) {
        if ((client.getAccount().getBalance() - omdb.getValue()) <= 0)
            throw new ObjectNotFoundExcepetion("Current balance is less than movie value, movie value is:"+omdb.getValue() +", maximum allowable movie value is:" + client.getAccount().getBalance());
    }

    private void checkIfInNotSameMovie (Client client, OMDB omdb){
        OMDB omdb1 = find(omdb.getImdbID());

        for (Client client1 : omdb1.getClients()) {
            if (client1 == client)
                throw new ObjectNotFoundExcepetion("The movie already exists for client.");
        }
    }
}
