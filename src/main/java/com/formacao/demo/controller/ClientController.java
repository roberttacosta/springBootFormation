package com.formacao.demo.controller;

import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.integration.configuration.OMDBApi;
import com.formacao.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController{
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "{/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client find(@PathVariable Integer id) {
        return clientService.find(id);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @GetMapping(value = "/{CPF}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client findByCPF(@PathVariable String CPF) {

        return clientService.findByCPF(CPF);
    }



    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Client insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
        return clientService.createClientAndAcconut(clientNewDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client update(@Valid @RequestBody Client client, @PathVariable Integer id) {
        return clientService.update(client);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Client delete(@PathVariable Integer id) {
        return clientService.deleteClientAccountTransaction(id);
    }

}
