package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    public Client find(Integer id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Client.class.getName()));
    }

    public Client findByCPF(String CPF) {
        Optional<Client> obj = Optional.ofNullable(clientRepository.findByCpf(CPF));
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + CPF + ". Tipo:" + Client.class.getName()));
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client insert(Client client) {
        accountRepository.save(client.getAccount());
        return clientRepository.save(client);
    }

    public void updateData(Client newClient, Client client) {
        newClient.setName(client.getName());
    }

    public Client update(Client client) {
        Client newClient = find(client.getId());
        updateData(newClient, client);
        return clientRepository.save(newClient);
    }

    public void deleteClientAccount(Integer id) {
        Client client = find(id);
        transactionService.delete(client);
        clientRepository.deleteById(id);
        accountService.delete(client);
    }

    public Client buildClient(ClientNewDTO clientNewDTO) {
        Account account = new Account(null, LocalDateTime.now(), clientNewDTO.getBalance());
        Client client = new Client(null, clientNewDTO.getName(), clientNewDTO.getCpf(), account);

        return client;
    }
}
