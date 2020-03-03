package com.formacao.demo.service.impl;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.AccountService;
import com.formacao.demo.service.TransactionService;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.formacao.demo.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;
//    private AccountRepository accountRepository;
//    private AccountServiceImpl accountServiceImpl;

    @Autowired
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountService accountService) {
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


    private Transaction buildTransaction(TransactionDTO transactionDTO, Account sourceAccount, Account targetAccount) {
        return new Transaction(null, sourceAccount, targetAccount, transactionDTO.getTransactionAmount(), LocalDateTime.now(), transactionDTO.getTypeTransaction());
    }

    private void updateBalanceByTransaction(Account account) {
        accountService.updateBalance(account);
    }

    public Account withdraw(Transaction transaction) {
        Double balance = transaction.getSourceAccount().getBalance();
        Double valor = transaction.getTransactionAmount();

        if (!sourceAccountIsNotNegative(transaction))
            throw new ObjectNotFoundExcepetion("Current balance is less than withdrawal amount, maximum allowable withdrawal amount is:" + balance);

        transaction.getSourceAccount().setBalance((balance - valor));

        return transaction.getSourceAccount();
    }

    public Account deposit(Transaction transaction) {
        Account account = transaction.getSourceAccount();
        Double balance = account.getBalance();
        Double valor = transaction.getTransactionAmount();

        if (accountServiceImpl.sourceAccountExists(transaction)) {
            account.setBalance(balance + valor);
        } else throw new ObjectNotFoundExcepetion("Source account not exists");

        return transaction.getSourceAccount();
    }

    public Account transfer(Transaction transaction) {
        Account sourceAccount = transaction.getSourceAccount();
        Double balanceSourceAccount = sourceAccount.getBalance();
        Account targetAccount = transaction.getTargetAccount();
        Double balanceTargetAccount = targetAccount.getBalance();
        Double valor = transaction.getTransactionAmount();

        System.out.println(sourceAccount);
        System.out.println(balanceSourceAccount);
        System.out.println(targetAccount);
        System.out.println(balanceTargetAccount);
        System.out.println(valor);

        this.validateIfAccountExists(transaction.getSourceAccount());
        this.validateIfAccountExists(transaction.getTargetAccount());

        if (transaction.getSourceAccount() == transaction.getTargetAccount())
            throw new ObjectNotFoundExcepetion("CONTAS IGUAIS");


        if (sourceAccountIsNotNegative(transaction)) {
            sourceAccount.setBalance((balanceSourceAccount - valor));
            targetAccount.setBalance((balanceTargetAccount + valor));
        } else
            throw new ObjectNotFoundExcepetion("Current balance is less than transfer amount, maximum allowable transfer amount is:" + balanceSourceAccount);

        return transaction.getSourceAccount();
    }

    public void validateIfAccountExists(Account account) {
        accountService.find(account.getId());
    }

    public Account doTransaction(Transaction transaction) {

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

    public boolean sourceAccountIsNotNegative(Transaction transaction) {
        Account account = transaction.getSourceAccount();
        Double balance = account.getBalance();
        Double transactionAmount = transaction.getTransactionAmount();
        if ((balance - transactionAmount) > 0) {
            return true;
        } else return false;
    }

    public void delete(Integer id) {
        find(id);
        transactionRepository.deleteById(id);
    }


}
