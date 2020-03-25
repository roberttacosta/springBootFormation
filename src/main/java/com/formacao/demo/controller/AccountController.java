package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Account find() {
        return accountService.findController();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping (value = "all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping(value = "statement")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> statement() {
        return accountService.bankStatement();
    }

    @GetMapping(value = "statement/{startDate}/to/{endDate}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> statementByDate(@PathVariable String startDate, @PathVariable String endDate) {
        return accountService.bankStatementByDate(startDate, endDate);
    }
}
