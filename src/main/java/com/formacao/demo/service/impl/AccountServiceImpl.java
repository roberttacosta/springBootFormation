package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.formacao.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
//    @Autowired
    private AccountRepository accountRepository;
//    @Autowired
    private TransactionService transactionService;

    @Autowired
    public void setTransactionService(AccountRepository accountRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }


    @Override
    public Account find(Integer id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Account.class.getName()));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> bankStatement(Integer id) {
        return transactionService.findAllBySourceAccount(this.find(id));
    }

//    @Override
//    public List<Transaction> bankStatementByDate(LocalDateTime dtInic, LocalDateTime dtFinal) {
//        return transactionService.findByDate(dtInic, dtFinal);
//    }

    @Override
    public Account updateBalance(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Client client) {
        accountRepository.deleteById(client.getAccount().getId());
    }

    @Override
    public Account create(Client client){
        return accountRepository.save(client.getAccount());
    }

}
