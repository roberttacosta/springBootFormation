package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account find(@PathVariable Integer id) {
        return accountService.find(id);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping(value = "statement/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> statement(@PathVariable Integer id) {
        return accountService.bankStatement(id);
    }

//    @GetMapping(value = "statement/dateinitial/{dtInic}/to/{}")
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    public List<Transaction> statementByDate(@PathVariable LocalDateTime dtInic) {
//        return accountService.bankStatementByDate(dtInic);
//    }
}
