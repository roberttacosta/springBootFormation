package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.exceptions.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;

    @Override
    public Account find(Integer id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExcepetion("An account with the id: "+id+ " was not found"));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> bankStatement(Integer id) {
        return transactionService.findAllBySourceAccount(this.find(id));
    }

//    @Override
//    public List<Transaction> bankStatementByDate(LocalDateTime startDate, LocalDateTime endDate) {
//        return transactionService.findByDate(startDate, endDate);
//    }

    @Override
    public void delete(Client client) {
        accountRepository.deleteById(client.getAccount().getId());
    }

    @Override
    public Account create(Client client){
        return accountRepository.save(client.getAccount());
    }

}
