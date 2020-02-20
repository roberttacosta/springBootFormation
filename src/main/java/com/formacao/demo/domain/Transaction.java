package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.formacao.demo.domain.enums.TypeTransaction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransaction;

    @ManyToOne
    @JoinColumn(name = "idSourceAccount")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "idTargetAccount")
    private Account targetAccount;

    private double transactionAmount;

    private Date transactionDate;
    private Integer typeTransaction;

    public Transaction(){

    }
    public Transaction(Integer idTransaction, Account sourceAccount, Account targetAccount, double transactionAmount, Date transactionDate, TypeTransaction typeTransaction) {
        this.idTransaction = idTransaction;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.typeTransaction = (typeTransaction == null) ? null : typeTransaction.getCodeType();
    }

    public int getIdTransaction() {return idTransaction;}
    public void setIdTransaction(Integer idTransaction) {this.idTransaction = idTransaction;}
    public Account getSourceAccount() {return sourceAccount;}
    public void setSourceAccount(Account sourceAccount) {this.sourceAccount = sourceAccount;}
    public Account getTargetAccount() {return targetAccount;}
    public void setTargetAccount(Account targetAccount) {this.targetAccount = targetAccount;}
    public double getTransactionAmount() {return transactionAmount;}
    public void setTransactionAmount(double transactionAmount) {this.transactionAmount = transactionAmount;}
    public Date getTransactionDate() {return transactionDate;}
    public void setTransactionDate(Date transactionDate) {this.transactionDate = transactionDate;}
    public Integer getTypeTransaction() {return typeTransaction;}
    public void setTypeTransaction(Integer typeTransaction) {this.typeTransaction = typeTransaction;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return idTransaction == transaction.idTransaction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction);
    }
}
