package com.formacao.demo.service;

import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;

import java.util.List;

public interface ClientService {

    Client find(Integer id);

    Client findByCPF(String CPF);

    Client createClientAndAcconut(ClientNewDTO clientNewDTO);

    Client update(Client client);

    void deleteClientAccountTransaction(Integer id);

    List<Client> findAll();

}
