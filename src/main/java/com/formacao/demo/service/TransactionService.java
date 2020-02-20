package com.formacao.demo.service;

import com.formacao.demo.domain.Transaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    private Transaction find(Integer id){
        Optional<Transaction> obj = transactionRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Transaction.class.getName()));
    }

    private Transaction insert (Transaction obj){
        obj.setIdTransaction(null);
        return transactionRepository.save(obj);
    }

    public void delete (Integer id){
        find(id);
        transactionRepository.deleteById(id);
    }

    private double withdraw(){
        return 1;
    }

    private double deposit(){
        return 1;
    }

    private double transfer(){
        return 1;
    }
}
