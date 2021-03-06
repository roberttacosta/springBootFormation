package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;

import java.util.List;

public interface AccountService {

    Account find(Integer id);

    Account findController();

    List<Account> findAll();

    List<Transaction> bankStatement();

    List<Transaction> bankStatementByDate(String startDate, String endDate);

    void delete(Client client);

    Account create(Client client);
}
