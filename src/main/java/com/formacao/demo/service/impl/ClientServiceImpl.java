package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.ClientRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    public Client find(Integer id) {
        Optional<Client> obj = clientRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Client.class.getName()));
    }

    public Client findByCPF(String CPF) {
        Optional<Client> obj = Optional.ofNullable(clientRepository.findByCpf(CPF));
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + CPF + ". Tipo:" + Client.class.getName()));
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
        clientRepository.deleteById(id);
        accountServiceImpl.delete(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client buildClient(ClientNewDTO clientNewDTO) {

        return new Client(null, clientNewDTO.getName(), clientNewDTO.getCpf(),
                                new Account(null, LocalDateTime.now(), clientNewDTO.getBalance()));
    }
}
