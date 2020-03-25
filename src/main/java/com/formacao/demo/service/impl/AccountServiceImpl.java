package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.security.UserSpringSecurity;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.ClientService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.UserService;
import com.formacao.demo.service.exceptions.AuthorizationException;
import com.formacao.demo.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;

    @Override
    public Account find(Integer id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("An account with the id: " + id + " was not found"));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> bankStatement() {
        UserSpringSecurity userSpringSecurity = userService.authenticated();
        if(userSpringSecurity == null){
            throw new AuthorizationException("Acesso negado!");
        }
        Client client = clientService.findByCPF(userSpringSecurity.getUsername());
        return transactionService.findAllBySourceAccount(this.find(client.getAccount().getId()));
    }

    @Override
    public List<Transaction> bankStatementByDate(Integer id, String startDate, String endDate) {
        return transactionService.findByDate(this.find(id), startDate, endDate);
    }

    @Override
    public void delete(Client client) {
        accountRepository.deleteById(client.getAccount().getId());
    }

    @Override
    public Account create(Client client) {
        return accountRepository.save(client.getAccount());
    }

}
