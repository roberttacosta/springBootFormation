package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundException;
import com.formacao.demo.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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

    private Transaction transaction, transaction2;
    private Client client;
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
        transaction2 = buildTransaction2();
        transactionDTO = buildTransactionDTO();
        client = builClient();
    }

    private Transaction buildTransaction() {
        return new Transaction(123, null, null, 0.00, LocalDateTime.now(), null);
    }

    private Transaction buildTransaction2() {
        return new Transaction(124, null, null, 0.00, LocalDateTime.now(), null);
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

    private Client builClient(){
        return new Client(1, "Rafael Teste", "12659459690", "rafael@gmail.com", "123" ,sourceAccount);
    }

    @Test
    public void find_caseTransactionExistsReturnSucess(){
        transaction.setSourceAccount(sourceAccount);
        transaction.setTransactionAmount(200.00);
        transaction.setTypeTransaction(TypeTransaction.WITHDRAW);

        Mockito.when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));

        final Transaction response = transactionServiceImpl.find(123);

        Assert.assertNotNull(response);
        Assert.assertEquals(transaction, response);
    }

    @Test
    public void find_caseTransactionNotExistsThrowException(){
        Mockito.when(transactionRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        thrown.expect(ObjectNotFoundException.class);
        thrown.expectMessage("A transaction with the id: " + 123 + " was not found");

        transactionServiceImpl.find(123);
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

        thrown.expect(DataIntegrityException.class);
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

        thrown.expect(DataIntegrityException.class);
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

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("Current balance is less than transaction amount, maximum allowable transaction amount is: " + sourceAccount.getBalance());

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

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("The transaction amount must be greater than zero.");

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnExceptionIsNotNegative() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(targetAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountIsNotNegative);
        transactionDTO.setTypeTransaction(TypeTransaction.TRANSFER);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(targetAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("Current balance is less than transaction amount, maximum allowable transaction amount is: " + sourceAccount.getBalance());

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void insert_caseCreateNewTransactionTransferReturnExceptionIsTheSameTargetAccount() {
        transactionDTO.setIdSourceAccount(sourceAccount.getId());
        transactionDTO.setIdTargetAccount(sourceAccount.getId());
        transactionDTO.setTransactionAmount(transactionAmountNormal);
        transactionDTO.setTypeTransaction(TypeTransaction.TRANSFER);

        when(accountService.find(transactionDTO.getIdSourceAccount())).thenReturn(sourceAccount);
        when(accountService.find(transactionDTO.getIdTargetAccount())).thenReturn(sourceAccount);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        thrown.expect(DataIntegrityException.class);
        thrown.expectMessage("The target account cannot be the same as the source account");

        transactionServiceImpl.insert(transactionDTO);
    }

    @Test
    public void findAllBySourceAccount_listTransactionsSuccess(){
        transaction.setSourceAccount(sourceAccount);
        transaction.setTransactionAmount(200.00);
        transaction.setTypeTransaction(TypeTransaction.WITHDRAW);

        transaction2.setSourceAccount(sourceAccount);
        transaction2.setTransactionAmount(300.00);
        transaction2.setTypeTransaction(TypeTransaction.DEPOSIT);

        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(transaction);
        transactions.add(transaction2);

        Mockito.when(transactionRepository.findAllBySourceAccount(sourceAccount)).thenReturn(transactions);

        final List<Transaction> responseTransactionList = transactionServiceImpl.findAllBySourceAccount(sourceAccount);

        Assert.assertNotNull(responseTransactionList);
        Assert.assertEquals(transactions, responseTransactionList);
    }

    @Test
    public void delete_deleteListTransaction(){
        transaction.setSourceAccount(sourceAccount);
        transaction.setTransactionAmount(200.00);
        transaction.setTypeTransaction(TypeTransaction.WITHDRAW);

        transaction2.setSourceAccount(sourceAccount);
        transaction2.setTransactionAmount(300.00);
        transaction2.setTypeTransaction(TypeTransaction.DEPOSIT);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(transaction2);

        when(accountService.bankStatement()).thenReturn(transactions);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        transactionServiceImpl.delete(client);

        verify(transactionRepository, times(2)).deleteById(argumentCaptor.capture());
    }

    @Test
    public void findByDate_listTransactionsSuccess(){
        transaction.setSourceAccount(sourceAccount);
        transaction.setTransactionAmount(200.00);
        transaction.setTypeTransaction(TypeTransaction.WITHDRAW);

        transaction2.setSourceAccount(sourceAccount);
        transaction2.setTransactionAmount(300.00);
        transaction2.setTypeTransaction(TypeTransaction.DEPOSIT);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        transactions.add(transaction2);

        String startDate = "2020-03-18";
        String endDate = "2020-03-20";

        Mockito.when(transactionRepository.findByDate(sourceAccount.getId(), startDate, endDate)).thenReturn(transactions);

        final List<Transaction> responseTransactionList = transactionServiceImpl.findByDate(sourceAccount, startDate, endDate);

        Assert.assertNotNull(responseTransactionList);
        Assert.assertEquals(transactions, responseTransactionList);
    }

}
