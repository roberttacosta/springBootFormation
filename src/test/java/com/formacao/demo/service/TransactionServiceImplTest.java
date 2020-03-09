package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private Account sourceAccount, targetAccount;

    @Before
    public void setUp() {
        sourceAccount = buildSourceAccount();
        targetAccount = buildTargetAccount();
        transaction = buildTransaction();
        transactionDTO = buildTransactionDTO();

    }

    private Transaction buildTransaction() {
        return new Transaction(123, null, null, 200.00, LocalDateTime.now(), null);
    }

    private TransactionDTO buildTransactionDTO() {
        return new TransactionDTO(null, null, 200.00, null);
    }

    private Account buildSourceAccount() {
        return new Account(4738, LocalDateTime.now(), 150.00);
    }

    private Account buildTargetAccount() {
        return new Account(9873, LocalDateTime.now(), 900.00);
    }

    @Test
    public void insert_caseCreateNewTransactionDepositReturnSuccess() {
        transactionDTO.setTypeTransaction(TypeTransaction.DEPOSIT);
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

        transactionServiceImpl.insert(transactionDTO);

        verify(accountService, times(1)).updateBalance(argumentCaptor.capture());
        Assert.assertTrue( 350.00 == argumentCaptor.getValue().getBalance());
    }
}
