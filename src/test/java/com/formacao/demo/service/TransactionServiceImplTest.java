package com.formacao.demo.service;

import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;

    private Transaction transaction;
    private TransactionDTO transactionDTO;

    @Before
    public void setUp(){
        transaction = buildTransaction();
        transactionDTO = buildTransactionDTO();
    }

    private Transaction buildTransaction(){
        return new Transaction(123, null, null,200.00, LocalDateTime.now(), null);
    }

    private TransactionDTO buildTransactionDTO() {
      return new TransactionDTO  (1, null, 200.00, null);
    }

    @Test
    public void insert_caseCreateNewTransactionDepositReturnSuccess(){
        Mockito.when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(transaction);


    }
}
