package com.formacao.demo.controller;

import com.formacao.demo.domain.Client;
import com.formacao.demo.dto.ClientNewDTO;
import com.formacao.demo.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @RequestMapping(value="id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> find(@PathVariable Integer id){
        Client obj = clientService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Client>> findAll() {
        List<Client> list = clientService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="cpf/{CPF}", method = RequestMethod.GET)
    public ResponseEntity<Client> findByCPF(@PathVariable String CPF){
        Client obj = clientService.findByCPF(CPF);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Client insert(@Valid @RequestBody ClientNewDTO clientNewDTO){
        Client client = clientService.buildClient(clientNewDTO);
        client = clientService.insert(client);
        return client;
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Client update(@Valid @RequestBody Client client, @PathVariable Integer id){
        return clientService.update(client);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientService.deleteClientAccount(id);
    }

}
