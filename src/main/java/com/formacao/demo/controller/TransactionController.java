package com.formacao.demo.controller;

import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.service.impl.AccountServiceImpl;
import com.formacao.demo.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;
    @Autowired
    private AccountServiceImpl accountServiceImpl;


    @RequestMapping (method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction insert(@Valid @RequestBody TransactionDTO transactionDTO){
        Transaction transaction = transactionService.insert(transactionDTO);

//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionService.buildTransaction(transactionDTO, accountService.find(transactionDTO.getIdSourceAccount()), accountService.find(transactionDTO.getIdTargetAccount())).getId()).toUri();
        return transaction;
    }
}
