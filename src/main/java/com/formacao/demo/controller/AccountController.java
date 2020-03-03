package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountServiceImpl accountServiceImpl;

    @Autowired
    public AccountController(AccountServiceImpl accountServiceImpl){
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account find(@PathVariable Integer id) {
        return accountServiceImpl.find(id);
    }

    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> list = accountServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "statement/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> statement(@PathVariable Integer id) {
        List<Transaction> list = accountServiceImpl.bankStatement(id);
        return ResponseEntity.ok().body(list);
    }

//    @RequestMapping (method = RequestMethod.POST)
//    public ResponseEntity<Void> insert(@Valid @RequestBody Account obj){
//        Account account = accountService.insert(obj);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }
}
