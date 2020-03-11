package com.formacao.demo.repository;


import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findAllBySourceAccount(Account account);

//    @Query (value = "SELECT * FROM Transaction t WHERE t.transactionDate BETWEEN ':startDate' AND ':endDate'", nativeQuery = true)
//    List<Transaction> findByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
