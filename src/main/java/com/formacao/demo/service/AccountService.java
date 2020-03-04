package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {

    Account find(Integer id);

    List<Account> findAll();

    List<Transaction> bankStatement(Integer id);

//    List<Transaction> bankStatementByDate(LocalDateTime dtInic, LocalDateTime dtFinal);

    Account updateBalance(Account account);

    void delete(Client client);

    Account create(Client client);

}
