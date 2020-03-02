package com.formacao.demo.service;

import com.formacao.demo.domain.Account;
import com.formacao.demo.domain.Transaction;
import com.formacao.demo.domain.enums.TypeTransaction;
import com.formacao.demo.dto.TransactionDTO;
import com.formacao.demo.repository.AccountRepository;
import com.formacao.demo.repository.TransactionRepository;
import com.formacao.demo.service.excepetion.ObjectNotFoundExcepetion;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;


    private Transaction find(Integer id) {
        Optional<Transaction> obj = transactionRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcepetion("Objeto não encontrado:" + id + ". Tipo:" + Transaction.class.getName()));
    }

    public Transaction insert(TransactionDTO transactionDTO) {
        Account sourceAccount = accountService.find(transactionDTO.getIdSourceAccount());
        Account targetAccount = transactionDTO.getIdTargetAccount() == 0 ? null : accountService.find(transactionDTO.getIdTargetAccount());
        Transaction transaction = buildTransaction(transactionDTO, sourceAccount, targetAccount);

        updateBalanceByTransaction(doTransaction(transaction));
        return transactionRepository.save(transaction);
    }

    public Transaction buildTransaction(TransactionDTO transactionDTO, Account sourceAccount, Account targetAccount) {
        return new Transaction(null, sourceAccount, targetAccount, transactionDTO.getTransactionAmount(), LocalDateTime.now(), transactionDTO.getTypeTransaction());
    }

    public void updateBalanceByTransaction(Account account) {
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

        if (accountService.sourceAccountExists(transaction)) {
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

        if (accountService.sourceAccountExists(transaction) && accountService.targetAccountExists(transaction)) {
            if (sourceAccountIsNotNegative(transaction)) {
                sourceAccount.setBalance((balanceSourceAccount - valor));
                targetAccount.setBalance((balanceTargetAccount + valor));
            } else
                throw new ObjectNotFoundExcepetion("Current balance is less than transfer amount, maximum allowable transfer amount is:" + balanceSourceAccount);
        } else throw new ObjectNotFoundExcepetion("Source account or target account no exists");
        return transaction.getSourceAccount();
    }

    public Account doTransaction(Transaction transaction) {

        switch (transaction.getTypeTransaction()){
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
