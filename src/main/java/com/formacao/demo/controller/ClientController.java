package com.formacao.demo.controller;

import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends ResourceSupport {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

//    @GetMapping(value = "{/{id}")
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public Client find(@PathVariable Integer id) {
//        return clientService.find(id);
//    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Resource> findAll() {
        List<Client> clientList = clientService.findAll();
        ArrayList<Resource> clientArrayList = new ArrayList<>();

        for (Client client : clientList){
            String cpf = client.getCpf();
            Integer id = client.getId();
            Resource<Client> clientResource = new Resource<Client>(client);

            clientResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).findByCPF(cpf)).withRel("Cliente por id:"));
            clientResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).delete(id)).withRel("delete"));
            clientArrayList.add(clientResource);
        }
        return clientArrayList;

    }

    @GetMapping(value = "/{CPF}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Resource<Client> findByCPF(@PathVariable String CPF) {
        Client client = clientService.findByCPF(CPF);
        Resource<Client> clientResource = new Resource<Client>(client);

        ControllerLinkBuilder allClients = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).findAll());
        clientResource.add(allClients.withRel("all-clients"));

        ControllerLinkBuilder accountClient = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AccountController.class).find(client.getAccount().getId()));
        clientResource.add(accountClient.withRel("account-client"));

        return clientResource;
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
