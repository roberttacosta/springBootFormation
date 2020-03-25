package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Client;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.exceptions.DataIntegrityException;
import com.formacao.demo.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Override
    public Transaction find(Integer id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("A transaction with the id: " + id + " was not found"));
    }

    @Override
    public Transaction insert(TransactionDTO transactionDTO) {
        Account sourceAccount = accountService.find(transactionDTO.getIdSourceAccount());
        Account targetAccount = transactionDTO.getIdTargetAccount() == null ? null : accountService.find(transactionDTO.getIdTargetAccount());

        Transaction transaction = this.buildTransaction(transactionDTO, sourceAccount, targetAccount);

        this.doTransaction(transaction);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAllBySourceAccount(Account account) {
        return transactionRepository.findAllBySourceAccount(account);
    }

    @Override
    public List<Transaction> findByDate (Account account, String startDate, String endDate) {
        return transactionRepository.findByDate(account.getId(), startDate, endDate);
    }

    @Override
    public void delete(Client client) {
        accountService.bankStatement()
                .stream().forEach(transaction -> transactionRepository.deleteById(transaction.getId()));
    }


    private Transaction buildTransaction(TransactionDTO transactionDTO, Account sourceAccount, Account targetAccount) {
        return new Transaction(null, sourceAccount, targetAccount, transactionDTO.getTransactionAmount(), LocalDateTime.now(), transactionDTO.getTypeTransaction());
    }

    private Account doTransaction(Transaction transaction) {
        switch (transaction.getTypeTransaction()) {
            case WITHDRAW:
                return withdraw(transaction);
            case DEPOSIT:
                return deposit(transaction);
            case TRANSFER:
                return transfer(transaction);
        }
        return null;
    }

    private Account withdraw(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);
        this.checkIfSourceAccountIsNotNegative(transaction);

        transaction.getSourceAccount().setBalance((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()));

        return transaction.getSourceAccount();
    }

    private Account deposit(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);

        transaction.getSourceAccount().setBalance(
                transaction.getSourceAccount().getBalance() + transaction.getTransactionAmount()
        );

        return transaction.getSourceAccount();
    }

    private Account transfer(Transaction transaction) {
        this.checkIfTransactionAmountGreaterThanZero(transaction);
        this.checkIfSourceAccountIsTheSameTargetAccount(transaction);
        this.checkIfSourceAccountIsNotNegative(transaction);

        transaction.getSourceAccount().setBalance((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()));
        transaction.getTargetAccount().setBalance((transaction.getTargetAccount().getBalance() + transaction.getTransactionAmount()));

        return transaction.getSourceAccount();
    }

    private void checkIfSourceAccountIsTheSameTargetAccount(Transaction transaction) {
        if (transaction.getSourceAccount() == transaction.getTargetAccount())
            throw new DataIntegrityException("The target account cannot be the same as the source account");
    }

    private void checkIfSourceAccountIsNotNegative(Transaction transaction) {
        if ((transaction.getSourceAccount().getBalance() - transaction.getTransactionAmount()) <= 0)
            throw new DataIntegrityException("Current balance is less than transaction amount, maximum allowable transaction amount is: " + transaction.getSourceAccount().getBalance());
    }

    private void checkIfTransactionAmountGreaterThanZero(Transaction transaction) {
        if (transaction.getTransactionAmount() <= 0)
            throw new DataIntegrityException("The transaction amount must be greater than zero.");
    }
}
