package com.formacao.demo.controller;

import com.formacao.demo.domain.Client;
import com.formacao.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Client> find(@PathVariable Integer id){
        Client obj = clientService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/{CPF}", method = RequestMethod.GET)
    public ResponseEntity<Client> findByCPF(@PathVariable String CPF){
        Client obj = clientService.findByCPF(CPF);
        return ResponseEntity.ok().body(obj);
    }
}
