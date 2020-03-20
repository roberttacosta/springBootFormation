package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.service.exceptions.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.AccountServiceImpl;
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

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionService transactionService;

    private Account account, account2;
    private Transaction transaction, transaction2;
    private Client client;

    @Before
    public void setUP(){
        account = buildAccount();
        account2 = buildAccount2();
        transaction = buildTransaction();
        transaction2 = buildTransaction2();
        client = buildClient();
    }

    private Account buildAccount(){
        return new Account(784, LocalDateTime.now(), 480.00);
    }

    private Account buildAccount2(){
        return new Account(785, LocalDateTime.now(), 430.00);
    }

    private Client buildClient(){
        return new Client(1, "Rafael Teste", "12659459690", account);
    }

    private Transaction buildTransaction(){
        return new Transaction(78, account, null, 100.00, LocalDateTime.now(), TypeTransaction.WITHDRAW);
    }

    private Transaction buildTransaction2(){
        return new Transaction(79, account, null, 220.00, LocalDateTime.now(), TypeTransaction.DEPOSIT);
    }

    @Test
    public void find_caseAccountExistsReturnSuccess(){

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        final Account response = accountServiceImpl.find(784);

        Assert.assertNotNull(response);
        Assert.assertEquals(account, response);
    }

    @Test
    public void find_caseAccountNotExistsThrowException(){

        Mockito.when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("An account with the id: " + 783 + " was not found");

        accountServiceImpl.find(783);
    }

    @Test
    public void findAll_listAccountsSuccess(){
        ArrayList<Account> accounts = new ArrayList<Account>();

        accounts.add(account);
        accounts.add(account2);

        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        final List<Account> response = accountServiceImpl.findAll();

        Assert.assertNotNull(response);
        Assert.assertEquals(accounts, response);
    }

    @Test
    public void bankStatement_listTransactionsSuccess(){
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        transactions.add(transaction);
        transactions.add(transaction2);

        Mockito.when(transactionService.findAllBySourceAccount(account)).thenReturn(transactions);
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        final List<Transaction> response = accountServiceImpl.bankStatement(account.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(transactions, response);
    }

    @Test
    public void bankStatementByDate_listTransactionsSuccess(){
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);
        transactions.add(transaction2);

        String startDate = "2020-03-18";
        String endDate = "2020-03-20";

        Mockito.when(transactionService.findByDate(account, startDate, endDate)).thenReturn(transactions);
        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        final List<Transaction> response = accountServiceImpl.bankStatementByDate(account.getId(),startDate, endDate);

        Assert.assertNotNull(response);
        Assert.assertEquals(transactions, response);
    }

    @Test
    public void delete_deleteAccount(){

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);

        accountServiceImpl.delete(client);

        verify(accountRepository, times(1)).deleteById(argumentCaptor.capture());
    }

    @Test
    public void create_createAccount(){
        when(accountRepository.save(account)).thenReturn(account);

        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);

        accountServiceImpl.create(client);

        verify(accountRepository, times(1)).save(argumentCaptor.capture());
    }


}
