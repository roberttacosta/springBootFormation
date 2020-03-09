package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private Account account;

    @Before
    public void setUP(){
        account = buildAccount();
    }

    private Account buildAccount(){
        return new Account(784, LocalDateTime.now(), 480.00);
    }

    @Test
    public void find_caseAccountExistsReturnSucess(){

        Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        final Account response = accountServiceImpl.find(784);

        Assert.assertNotNull(response);
        Assert.assertEquals(account, response);
    }

    @Test
    public void find_caseAccountNotExistsThrowException(){

        Mockito.when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        thrown.expect(ObjectNotFoundExcepetion.class);
        thrown.expectMessage("Objeto não encontrado:" + 783 + ". Tipo:" + Account.class.getName());


       accountServiceImpl.find(783);
    }

}
