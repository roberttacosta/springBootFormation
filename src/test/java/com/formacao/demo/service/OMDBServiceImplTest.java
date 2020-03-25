package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.OMDB;
import com.formacao.demo.dto.ClientAndMovieDTO;
import com.formacao.demo.integration.configuration.OMDBApi;
import com.formacao.demo.integration.response.OMDBResponse;
import com.formacao.demo.repository.OMDBRepository;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundException;
import com.formacao.demo.service.impl.OMDBServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class OMDBServiceImplTest {

    @InjectMocks
    OMDBServiceImpl omdbServiceImpl;

    @Mock
    private OMDBRepository omdbRepository;
    @Mock
    private ClientService clientService;
    @Mock
    private OMDBApi omdbApi;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private OMDB omdb, omdb2;
    private OMDBResponse omdbResponse;
    private Client client;
    private Account account;
    private ClientAndMovieDTO clientAndMovieDTO;

    @Before
    public void setUp() {
        omdb = buildOmdb();
        omdb2 = buildOmdb2();
        omdbResponse = buildOmdbResponse();
        account = buildAccount();
        client = buildClient();
        clientAndMovieDTO = buildClientAndMovieDTO();
    }

    private ClientAndMovieDTO buildClientAndMovieDTO(){
        return new ClientAndMovieDTO( "Batman");
    }

    private OMDB buildOmdb(){
        return new OMDB("Batman", "2000", "Action", "J P", "Jsfsd", "saf", "ffdf", "dsfds", "dadas", "TM100", "saass", "sas", 50.00);
    }

    private OMDBResponse buildOmdbResponse(){
        return new OMDBResponse("Batman", "2000", "Action", "J P", "Jsfsd", "saf", "ffdf", "dsfds", "dadas", "TM100", "saass", "sas");
    }

    private OMDB buildOmdb2(){
        return new OMDB("The SpiderMan", "2000", "Action", "J P", "Jsfsd", "saf", "ffdf", "dsfds", "dadas", "TM100", "saass", "sas", 50.00);
    }

    private Client buildClient(){
        return new Client(1, "Rafael Teste", "12659459690", "rafael@gmail.com", "123", account);
    }

    private Account buildAccount() {
        return new Account(4738, LocalDateTime.now(), 150.00);
    }

    @Test
    public void find_caseOMDBExistsReturnSuccess(){

        Mockito.when(omdbRepository.findByTitle(omdb.getTitle())).thenReturn(omdb);

        final OMDB response = omdbServiceImpl.find(omdb.getTitle());

        Assert.assertNotNull(response);
        Assert.assertEquals(omdb, response);
    }

    @Test
    public void find_caseOMDBNotExistsThrowException(){

        Mockito.when(omdbRepository.findByTitle(ArgumentMatchers.any())).thenReturn(null);
        thrown.expect(ObjectNotFoundException.class);
        thrown.expectMessage("A movie with the title: " + omdb.getTitle() + " was not found");

        omdbServiceImpl.find(omdb.getTitle());
    }

    @Test
    public void findAll_listOMDBsSuccess(){
        ArrayList<OMDB> omdbs = new ArrayList<OMDB>();

        omdbs.add(omdb);
        omdbs.add(omdb2);

        Mockito.when(omdbRepository.findAll()).thenReturn(omdbs);

        final List<OMDB> response = omdbServiceImpl.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(omdbs, response);
    }

    @Test
    public void locateNewMovieByClient_locateWithSuccess() {
        Mockito.when(clientService.findByCPF(client.getCpf())).thenReturn(client);
        Mockito.when(omdbApi.findByName("Batman", "a549d02f")).thenReturn(omdbResponse);

        ArgumentCaptor<OMDB> argumentCaptor = ArgumentCaptor.forClass(OMDB.class);

        omdbServiceImpl.locateNewMovieByClient(clientAndMovieDTO);

        verify(omdbRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    public void locateNewMovieByClient_caseClientAlreadyLocate() {
        Mockito.when(clientService.findByCPF(client.getCpf())).thenReturn(client);
        Mockito.when(omdbApi.findByName("Batman", "a549d02f")).thenReturn(omdbResponse);

        ArrayList<OMDB> omdbs = new ArrayList<OMDB>();
        omdbs.add(omdb);
        client.setOmdbs(omdbs);

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("The film: "+ omdb.getTitle() +" has already been rented by the customer: " + client.getName());


        omdbServiceImpl.locateNewMovieByClient(clientAndMovieDTO);
    }

    @Test
    public void locateNewMovieByClient_caseAccountsIsNegative() {
        client.getAccount().setBalance(5.00);

        Mockito.when(clientService.findByCPF(client.getCpf())).thenReturn(client);
        Mockito.when(omdbApi.findByName("Batman", "a549d02f")).thenReturn(omdbResponse);

        thrown.expect(DataIntegrityException.class);

        omdbServiceImpl.locateNewMovieByClient(clientAndMovieDTO);
    }




}
