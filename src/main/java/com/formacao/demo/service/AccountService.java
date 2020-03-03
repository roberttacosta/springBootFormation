package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;

import java.util.List;

public interface AccountService {

    Account find(Integer id);

    List<Account> findAll();

    List<Transaction> bankStatement(Integer id);

    Account updateBalance(Account account);

}
