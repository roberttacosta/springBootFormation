package com.formacao.demo.repository;


import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findAllBySourceAccount(Account account);

//    @Query ("SELECT t FROM Transaction t WHERE t.transactionDate <= :dtFinal ")
//    List<Transaction> findByDate(@Param("dtFinal") LocalDateTime dtFinal);
}
