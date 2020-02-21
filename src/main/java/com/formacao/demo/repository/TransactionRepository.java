package com.formacao.demo.repository;


import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySourceAccount(Account account);
}
