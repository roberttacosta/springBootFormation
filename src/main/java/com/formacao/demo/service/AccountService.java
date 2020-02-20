package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Account find(Integer id){
        Optional<Account> obj = accountRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Account.class.getName()));
    }

    public Account insert (Account obj){
        obj.setIdAccount(null);
        return accountRepository.save(obj);
    }

    public void updateData (Account newObj, Account obj) {
        newObj.setIdAccount(obj.getIdAccount());
    }

    public Account update(Account obj) {
        Account newObj = find(obj.getIdAccount());
        updateData(newObj, obj);
        return accountRepository.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        accountRepository.deleteById(id);
    }

}
