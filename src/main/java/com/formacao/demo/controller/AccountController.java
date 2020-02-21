package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value="id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> find(@PathVariable Integer id){
        Account obj = accountService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="statement/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> eita(@PathVariable Integer id){
        List<Transaction> list = accountService.bankStatement(id);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Account obj){
        Account account = accountService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
