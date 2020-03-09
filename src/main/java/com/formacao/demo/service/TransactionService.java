package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    Transaction find(Integer id);

    Transaction insert(TransactionDTO transactionDTO);

    List<Transaction> findAllBySourceAccount(Account account);

    void delete(Client client);

//    List<Transaction> findByDate (LocalDateTime startDate, LocalDateTime endDate);
}
