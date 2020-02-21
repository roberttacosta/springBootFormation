package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value="id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> find(@PathVariable Integer id){
        Client obj = clientService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="cpf/{CPF}", method = RequestMethod.GET)
    public ResponseEntity<Client> findByCPF(@PathVariable String CPF){
        Client obj = clientService.findByCPF(CPF);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Client obj){
        Client client = clientService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
