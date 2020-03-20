package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.ClientServiceImpl;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ClienteServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private TransactionService transactionService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Transaction transaction, transaction2;
    private Client client, clientNew;
    private TransactionDTO transactionDTO;
    private ClientNewDTO clientNewDTO;
    private Account account;

    @Before
    public void setUp() {
        account = buildAccount();
        transaction = buildTransaction();
        transaction2 = buildTransaction2();
        client = buildClient();
        clientNew = buildClientNew();
        clientNewDTO = buildClientNewDTO();
    }

    private Transaction buildTransaction() {
        return new Transaction(123, account, null, 90.00, LocalDateTime.now(), TypeTransaction.WITHDRAW);
    }

    private Transaction buildTransaction2() {
        return new Transaction(124, account, null, 80.00, LocalDateTime.now(), TypeTransaction.DEPOSIT);
    }

    private Account buildAccount() {
        return new Account(4738, LocalDateTime.now(), 150.00);
    }

    private Client buildClient(){
        return new Client(1, "Rafael Teste", "12659459690", account);
    }

    private Client buildClientNew(){
        return new Client(1, "Rafael Teste", "67611680668", account);
    }

    private ClientNewDTO buildClientNewDTO() {
        return new ClientNewDTO("Rafael Dto", "67611680668", 150.00);
    }

    @Test
    public void find_caseClientExistsReturnSuccess(){

        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        final Client response = clientServiceImpl.find(1);

        Assert.assertNotNull(response);
        Assert.assertEquals(client, response);
    }

    @Test
    public void find_caseAccountNotExistsThrowException(){

        Mockito.when(clientRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("A client with the id: " + 1 + " was not found");

        clientServiceImpl.find(1);
    }

    @Test
    public void findByCpf_caseClientExistsReturnSuccess(){

        Mockito.when(clientRepository.findByCpf(client.getCpf())).thenReturn(client);

        final Client response = clientServiceImpl.findByCPF(client.getCpf());

        Assert.assertNotNull(response);
        Assert.assertEquals(client, response);
    }

    @Test
    public void findByCpf_caseAccountNotExistsThrowException(){

        Mockito.when(clientRepository.findByCpf(ArgumentMatchers.any())).thenReturn(null);
        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("A client with the cpf: " + "12659459695" + " was not found");

        clientServiceImpl.findByCPF("12659459695");
    }

    @Test
    public void createClientAndAccount_createWithSuccess(){
        Mockito.when(accountService.create(clientNew)).thenReturn(account);
        Mockito.when(clientRepository.findByCpf(clientNewDTO.getCpf())).thenReturn(null);
        Mockito.when(clientRepository.saveAndFlush(clientNew)).thenReturn(clientNew);

        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);


        clientServiceImpl.createClientAndAcconut(clientNewDTO);

        verify(clientRepository, times(1)).saveAndFlush(argumentCaptor.capture());
        verify(accountService, times(1)).create(argumentCaptor.capture());
    }

    @Test
    public void createClientAndAccount_caseAlreadyCPFExists(){
        clientNewDTO.setCpf("12659459690");
        Mockito.when(accountService.create(client)).thenReturn(account);
        Mockito.when(clientRepository.findByCpf(clientNewDTO.getCpf())).thenReturn(client);
        Mockito.when(clientRepository.saveAndFlush(clientNew)).thenReturn(clientNew);

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("This CPF already exists in DataBase, insert a new CPF!");

        clientServiceImpl.createClientAndAcconut(clientNewDTO);

    }


    @Test
    public void updateClient_updateWithSuccess(){
        Mockito.when(clientRepository.save(client)).thenReturn(client);
        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);

        clientServiceImpl.update(client);

        verify(clientRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    public void deleteClientAccountTransaction_deleteAllWithSuccess(){

        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);


        clientServiceImpl.deleteClientAccountTransaction(client.getId());

        verify(transactionService, times(1)).delete(clientArgumentCaptor.capture());
        verify(clientRepository, times(1)).deleteById(integerArgumentCaptor.capture());
        verify(accountService, times(1)).delete(clientArgumentCaptor.capture());
    }

    @Test
    public void findAll_listClientsSucces(){
        ArrayList<Client> clients = new ArrayList<Client>();

        clients.add(client);
        clients.add(clientNew);

        Mockito.when(clientRepository.findAll()).thenReturn(clients);

        final List<Client> response = clientServiceImpl.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(clients, response);
    }

}
