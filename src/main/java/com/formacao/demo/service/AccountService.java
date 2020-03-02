package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Account find(Integer id) {
        Optional<Account> obj = accountRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Account.class.getName()));
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Transaction> bankStatement(Integer id) {
        Account account = find(id);
        return transactionRepository.findAllBySourceAccount(account);
    }

    public boolean sourceAccountExists(Transaction transaction) {
        if (find(transaction.getSourceAccount().getId()) != null) {
            return true;
        } else return false;
    }

    public boolean targetAccountExists(Transaction transaction) {
        if (find(transaction.getTargetAccount().getId()) != null) {
            return true;
        } else return false;
    }

    public Account updateBalance(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Client client) {
        accountRepository.deleteById(client.getAccount().getId());
    }

}
