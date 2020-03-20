package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.ClientService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundExcepetion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private AccountService accountService;
    private TransactionService transactionService;

    public ClientServiceImpl(ClientRepository clientRepository, AccountService accountService, TransactionService transactionService) {
        this.clientRepository = clientRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public Client find(Integer id) {
        return clientRepository.findById(id).orElseThrow(() -> new ObjectNotFoundExcepetion("A client with the id: " + id + " was not found"));
    }

    @Override
    public Client findByCPF(String cpf) {
        return Optional.ofNullable(clientRepository.findByCpf(cpf))
                .orElseThrow(() -> new ObjectNotFoundExcepetion("A client with the cpf: " + cpf + " was not found"));
    }

    @Override
    public Client createClientAndAcconut(ClientNewDTO clientNewDTO) {
        checkIfNotExistsCpfInDataBase(clientNewDTO);
        Client client = this.buildClient(clientNewDTO);
        accountService.create(client);
        return clientRepository.saveAndFlush(client);
    }

    private void updateData(Client newClient, Client client) {
        newClient.setName(client.getName());
    }

    @Override
    public Client update(Client client) {
        Client newClient = find(client.getId());
        updateData(newClient, client);
        return clientRepository.save(newClient);
    }

    @Override
    public void deleteClientAccountTransaction(Integer id) {
        Client client = this.find(id);
        transactionService.delete(client);
        clientRepository.deleteById(id);
        accountService.delete(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    private Client buildClient(ClientNewDTO clientNewDTO) {
        return new Client(null, clientNewDTO.getName(), clientNewDTO.getCpf(),
                new Account(null, LocalDateTime.now(), clientNewDTO.getBalance()));
    }

    private void checkIfNotExistsCpfInDataBase(ClientNewDTO clientNewDTO) {
        if (clientRepository.findByCpf(clientNewDTO.getCpf()) != null)
            throw new DataIntegrityException("This CPF already exists in DataBase, insert a new CPF!");
    }
}
