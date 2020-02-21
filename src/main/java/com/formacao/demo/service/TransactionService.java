package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
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

    public Transaction insertNewTransaction (Transaction obj){
        obj.setId(null);
        Integer typeTransaction = obj.getTypeTransaction();
        if (typeTransaction == 1){
            withdraw(obj);
        }
        else if (typeTransaction ==2){
            deposit(obj);
        }
        return transactionRepository.save(obj);

    }

    public void delete (Integer id){
        find(id);
        transactionRepository.deleteById(id);
    }

    private ObjectNotFoundExcepetion withdraw(Transaction obj){
       Account account = obj.getSourceAccount();
       Double balanceWithdraw = obj.getTransactionAmount();
       Double balance = account.getBalance();

        if((balance - balanceWithdraw) < 0){
            return new ObjectNotFoundExcepetion("Current balance is less than withdrawal amount, maximum allowable withdrawal amount is:" + balance);
        }
        else {
            account.setBalance((balance-balanceWithdraw));
        }
        return null;
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
