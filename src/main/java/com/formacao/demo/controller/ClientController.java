package com.formacao.demo.controller;

import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/clients")
public class ClientController {
    private ClientService clientService;
    @Deprecated
    private ControllerLinkBuilder controllerLinkBuilder;

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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Client> findAll() {
        List<Client> clientList = clientService.findAll();
        ArrayList<Client> clientArrayList = new ArrayList<>();
//
        for (Client client : clientList) {
            String cpf = client.getCpf();
            Integer id = client.getId();

            client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(this.getClass()).findByCPF(cpf)).withRel("Cliente por id:"));
//            client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(AccountController.class).find(client.getAccount().getId())).withRel("Account client:"));
            clientArrayList.add(client);
        }

        return clientArrayList;
    }

    @GetMapping(value = "/{CPF}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client findByCPF(@PathVariable String CPF) {
        Client client = clientService.findByCPF(CPF);

        client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(this.getClass()).findAll()).withRel("All clients:"));
//        client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(AccountController.class).find(client.getAccount().getId())).withRel("Client account:"));
        client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(this.getClass()).update(null, client.getId())).withRel("Update"));
//        client.add(controllerLinkBuilder.linkTo(controllerLinkBuilder.methodOn(AccountController.class).statement(client.getAccount().getId())).withRel("Statement"));

        return client;
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Client insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
        clientService.createClientAndAcconut(clientNewDTO);
        Client client = findByCPF(clientNewDTO.getCpf());

        return client;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client update(@Valid @RequestBody Client client, @PathVariable Integer id) {
        return clientService.update(client);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientService.deleteClientAccountTransaction(id);
    }

}
