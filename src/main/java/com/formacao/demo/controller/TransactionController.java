package com.formacao.demo.controller;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction insert(@Valid @RequestBody TransactionDTO transactionDTO){
        Transaction transaction = transactionService.insert(transactionDTO);

//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionService.buildTransaction(transactionDTO, accountService.find(transactionDTO.getIdSourceAccount()), accountService.find(transactionDTO.getIdTargetAccount())).getId()).toUri();
        return transaction;
    }
}
