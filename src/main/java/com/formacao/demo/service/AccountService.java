package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
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

    public Account insert(Account obj){
        obj.setId(null);
        return accountRepository.save(obj);
    }

    public void updateData(Account newObj, Account obj) {
        newObj.setId(obj.getId());
        newObj.setBalance(obj.getBalance());
    }

//    public Account update(Account obj) {
//        Account newObj = find(obj.getId());
//        updateData(newObj, obj);
//        return accountRepository.save(newObj);
//    }
//
    public Account updateBalance (Account account) {
        return accountRepository.save(account);
    }

    public void delete(Integer id) {
        find(id);
        accountRepository.deleteById(id);
    }

    public List<Transaction> bankStatement(Integer id){
        Account obj = find(id);
        return transactionRepository.findAllBySourceAccount(obj);
    }

}
