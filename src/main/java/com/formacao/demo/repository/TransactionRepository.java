package com.formacao.demo.repository;


import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySourceAccount(Account account);

//    @Query (value = "SELECT * FROM Transaction t WHERE t.transactionDate BETWEEN ':startDate' AND ':endDate'", nativeQuery = true)
    @Query(value = "SELECT * FROM transaction where id_source_account = :idAccount and transaction_date between to_date(:startDate, 'YYYY-MM-DD') and to_date(:endDate, 'YYYY-MM-DD')", nativeQuery = true)
    List<Transaction> findByDate(@Param("idAccount") Integer idAccount, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
