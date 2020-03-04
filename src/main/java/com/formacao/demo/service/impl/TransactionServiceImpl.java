package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @Autowired
    public void setAccountService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Transaction find(Integer id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Transaction.class.getName()));
    }

    @Override
    public Transaction insert(TransactionDTO transactionDTO) {
        Account sourceAccount = accountService.find(transactionDTO.getIdSourceAccount());
        Account targetAccount = transactionDTO.getIdTargetAccount() == 0 ? null : accountService.find(transactionDTO.getIdTargetAccount());

        Transaction transaction = this.buildTransaction(transactionDTO, sourceAccount, targetAccount);

        this.updateBalanceByTransaction(doTransaction(transaction));
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAllBySourceAccount(Account account) {
        return transactionRepository.findAllBySourceAccount(account);
    }

//    @Override
//    public List<Transaction> findByDate (LocalDateTime dtInic, LocalDateTime dtFinal) {
//        return transactionRepository.findByDate(dtInic, dtFinal);
//    }

    @Override
    public void delete(Client client) {
        List<Transaction> transactionList = accountService.bankStatement(client.getAccount().getId());
        for (Transaction transaction : transactionList) {
            transactionRepository.deleteById(transaction.getId());
        }
    }


    private Transaction buildTransaction(TransactionDTO transactionDTO, Account sourceAccount, Account targetAccount) {
        return new Transaction(null, sourceAccount, targetAccount, transactionDTO.getTransactionAmount(), LocalDateTime.now(), transactionDTO.getTypeTransaction());
    }

    private void updateBalanceByTransaction(Account account) {
        accountService.updateBalance(account);
    }

    private Account doTransaction(Transaction transaction) {
        switch (transaction.getTypeTransaction()) {
            case WITHDRAW:
                return withdraw(transaction);
            case DEPOSIT:
                return deposit(transaction);
            case TRANSFER:
                return transfer(transaction);
            default:
                throw new ObjectNotFoundExcepetion("Type transaction is invalid");
        }
    }

    private Account withdraw(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);
        this.checkIfAccountExists(transaction.getSourceAccount());
        this.checkIfSourceAccountIsNotNegative(transaction);

        transaction.getSourceAccount().setBalance((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()));

        return transaction.getSourceAccount();
    }

    private Account deposit(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);
        this.checkIfAccountExists(transaction.getSourceAccount());

        transaction.getSourceAccount().setBalance(transaction.getSourceAccount().getBalance() + transaction.getTransactionAmount());

        return transaction.getSourceAccount();
    }

    private Account transfer(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);
        this.checkIfAccountExists(transaction.getSourceAccount());
        this.checkIfAccountExists(transaction.getTargetAccount());
        this.checkIfSourceAccountIsTheSameTargetAccount(transaction);
        this.checkIfSourceAccountIsNotNegative(transaction);

        transaction.getSourceAccount().setBalance((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()));
        transaction.getTargetAccount().setBalance((transaction.getTargetAccount().getBalance() + transaction.getTransactionAmount()));

        return transaction.getSourceAccount();
    }

    private void checkIfSourceAccountIsTheSameTargetAccount(Transaction transaction) {
        if (transaction.getSourceAccount() == transaction.getTargetAccount())
            throw new ObjectNotFoundExcepetion("The target account cannot be the same as the source account");
    }

    private void checkIfAccountExists(Account account) {
        accountService.find(account.getId());
    }

    private void checkIfSourceAccountIsNotNegative(Transaction transaction) {
        if ((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()) <= 0)
            throw new ObjectNotFoundExcepetion("Current balance is less than transaction amount, maximum allowable transaction amount is:" + transaction.getSourceAccount().getBalance());
    }

    private void checkIfTransactionAmountGreaterThanZero(Transaction transaction) {
        if (transaction.getTransactionAmount() <= 0)
            throw new ObjectNotFoundExcepetion("The transaction amount must be greater than zero. ");
    }
}