package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.TransactionServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Transaction transaction;
    private TransactionDTO transactionDTO;
    private Account sourceAccount, targetAccount;
    private Double transactionAmountNormal = 50.00;
    private Double transactionAmountGreaterThanZero = -20.00;
    private Double transactionAmountIsNotNegative = 180.00;

    @Before
    public void setUp() {
        sourceAccount = buildSourceAccount();
        targetAccount = buildTargetAccount();
        transaction = buildTransaction();
        transactionDTO = buildTransactionDTO();
    }

    private Transaction buildTransaction() {
        return new Transaction(123, null, null, 0.00, LocalDateTime.now(), null);
    }

    private TransactionDTO buildTransactionDTO() {
        return new TransactionDTO(null, null, 0.00, null);
    }

    private Account buildSourceAccount() {
        return new Account(4738, LocalDateTime.now(), 150.00);
    }

    private Account buildTargetAccount() {
        return new Account(9873, LocalDateTime.now(), 900.00);
    }

    @Test
    public void insert_caseCreateNewTransactionDepositReturnSuccess() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountNormal);
        transactionDTO.setTypeTransaction(TypeTransaction.DEPOSIT);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        transactionServiceImpl.insert(transactionDTO);

        verify(transactionRepository, times(1)).save(argumentCaptor.capture());
        Assert.assertTrue(sourceAccount.getBalance() == argumentCaptor.getValue().getSourceAccount().getBalance());
    }

    @Test
    public void insert_caseCreateNewTransactionDepositReturnExceptionGreaterThanZero() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountGreaterThanZero);
        transactionDTO.setTypeTransaction(TypeTransaction.DEPOSIT);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("The transaction amount must be greater than zero.");

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionWithdrawReturnSuccess() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountNormal);
        transactionDTO.setTypeTransaction(TypeTransaction.WITHDRAW);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        transactionServiceImpl.insert(transactionDTO);
        verify(transactionRepository, times(1)).save(argumentCaptor.capture());

        Assert.assertTrue(sourceAccount.getBalance() == argumentCaptor.getValue().getSourceAccount().getBalance());
    }

    @Test
    public void insert_caseCreateNewTransactionWithdrawReturnExceptionGreaterThanZero() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountGreaterThanZero);
        transactionDTO.setTypeTransaction(TypeTransaction.WITHDRAW);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("The transaction amount must be greater than zero.");

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionWithdrawReturnExceptionIsNotNegative() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountIsNotNegative);
        transactionDTO.setTypeTransaction(TypeTransaction.WITHDRAW);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("Current balance is less than transaction amount, maximum allowable transaction amount is:" + sourceAccount.getBalance());

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnSuccess() {
        transactionDTO.setTypeTransaction(TypeTransaction.TRANSFER);
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(targetAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountNormal);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(targetAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        ArgumentCaptor<Transaction> argumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        transactionServiceImpl.insert(transactionDTO);
        verify(transactionRepository, times(1)).save(argumentCaptor.capture());

        Assert.assertTrue(sourceAccount.getBalance() == argumentCaptor.getValue().getSourceAccount().getBalance());
        Assert.assertTrue(targetAccount.getBalance() == argumentCaptor.getValue().getTargetAccount().getBalance());
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnExceptionGreaterThanZero() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(targetAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountGreaterThanZero);
        transactionDTO.setTypeTransaction(TypeTransaction.WITHDRAW);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(targetAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("The transaction amount must be greater than zero.");

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnExceptionIsNotNegative() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(targetAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountIsNotNegative);
        transactionDTO.setTypeTransaction(TypeTransaction.WITHDRAW);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(targetAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("Current balance is less than transaction amount, maximum allowable transaction amount is:" + sourceAccount.getBalance());

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnExceptionIsTheSameTargetAccount() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountNormal);
        transactionDTO.setTypeTransaction(TypeTransaction.TRANSFER);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
//        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

//        thrown.expect(ObjectNotFoundExcepetion.class);
//        thrown.expectMessage("The target account cannot be the same as the source account");

        transactionServiceImpl.insert(transactionDTO);
    }

}
