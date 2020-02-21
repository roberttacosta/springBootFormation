package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
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

    public Transaction insert(Transaction obj){
        obj.setId(null);
        return transactionRepository.save(obj);
    }

    public Account validateInsert (Transaction obj){
        Account account = obj.getSourceAccount();
        Double balance = account.getBalance();
        Double valor = obj.getTransactionAmount();
        if(obj.getTypeTransaction().equals(TypeTransaction.WITHDRAW)){
            if(isNotNegative(obj)){
               account.setBalance((balance - valor));
            }
            else {
                 throw new ObjectNotFoundExcepetion("Current balance is less than withdrawal amount, maximum allowable withdrawal amount is:" + balance);}

        }
        return account;
    }

    public boolean isNotNegative(Transaction obj){
        Account account = obj.getSourceAccount();
        Double balance = account.getBalance();
        Double transactionAmount = obj.getTransactionAmount();
        if((balance - transactionAmount) > 0){ return true;}
        else return false;
    }

    public void delete (Integer id){
        find(id);
        transactionRepository.deleteById(id);
    }


    private void deposit(Transaction obj){
        Account account = obj.getSourceAccount();
        Double balanceDeposit = obj.getTransactionAmount();
        Double balance = account.getBalance();

        account.setBalance((balance + balanceDeposit));

    }

    private double transfer(){
        return 1;
    }
}
