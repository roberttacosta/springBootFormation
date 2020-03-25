package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;
import com.formacao.demo.integration.configuration.OMDBApi;
import com.formacao.demo.integration.response.OMDBResponse;
import com.formacao.demo.repository.OMDBRepository;
import com.formacao.demo.security.UserSpringSecurity;
import com.formacao.demo.service.ClientService;
import com.formacao.demo.service.OMDBService;
import com.formacao.demo.service.UserService;
import com.formacao.demo.service.exceptions.AuthorizationException;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OMDBServiceImpl implements OMDBService {

    private OMDBRepository omdbRepository;
    private ClientService clientService;
    private OMDBApi omdbApi;
    private UserService userService;

    @Autowired
    public OMDBServiceImpl(OMDBRepository omdbRepository, ClientService clientService, OMDBApi omdbApi, UserService userService) {
        this.omdbRepository = omdbRepository;
        this.clientService = clientService;
        this.omdbApi = omdbApi;
        this.userService = userService;
    }

    @Override
    public OMDB find(String title) {
        return Optional.ofNullable(omdbRepository.findByTitle(title))
                .orElseThrow(() -> new ObjectNotFoundException("A movie with the title: " + title + " was not found"));
    }

    @Override
    public List<OMDB> findAll() {
        return omdbRepository.findAll();
    }

    @Override
    public OMDB locateNewMovieByClient(ClientAndMovieDTO clientAndMovieDTO) {
        Client client = this.clientLoged();
        OMDB omdb = builOmdb(omdbApi.findByName(clientAndMovieDTO.getTitle(), "a549d02f"));

        checkIfInNotSameMovie(client, omdb);
        checkIfAccountIsNotNegative(client, omdb);

        client.getAccount().setBalance((client.getAccount().getBalance() - omdb.getValue()));

        client.getOmdbs().add(omdb);
        omdb.getClients().add(client);

        return omdbRepository.save(omdb);
    }

    private OMDB builOmdb(OMDBResponse omdbResponse) {
        Random random = new Random();
        return new OMDB(omdbResponse.getTitle(), omdbResponse.getYear(), omdbResponse.getGenre(), omdbResponse.getDirector(), omdbResponse.getWriter(), omdbResponse.getActors(), omdbResponse.getPlot(), omdbResponse.getLanguage(), omdbResponse.getCountry(), omdbResponse.getPoster(), omdbResponse.getImdbID(), omdbResponse.getType(), (random.nextDouble() * 100));
    }

    private void checkIfAccountIsNotNegative(Client client, OMDB omdb) {
        if ((client.getAccount().getBalance() - omdb.getValue()) <= 0)
            throw new DataIntegrityException("Current balance is less than movie value, movie value is: " + omdb.getValue() + ", maximum allowable movie value is:" + client.getAccount().getBalance());
    }

    private void checkIfInNotSameMovie(Client client, OMDB omdb) {
        for (OMDB omdbActual : client.getOmdbs()) {
            if (omdbActual.getImdbID().equals(omdb.getImdbID()))
                throw new DataIntegrityException("The film: " + omdb.getTitle() + " has already been rented by the customer: " + client.getName());
        }
    }

    private Client clientLoged(){
        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if (userSpringSecurity == null) {
            throw new AuthorizationException("Acesso negado!");
        }
        return clientService.findByCPF(userSpringSecurity.getUsername());
    }
}
