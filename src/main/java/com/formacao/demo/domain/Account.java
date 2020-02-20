package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCreation;
    private double balance;
    @JsonIgnore
    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> transactionsSource;
    @JsonIgnore
    @OneToMany(mappedBy = "targetAccount")
    private List<Transaction> transactionTarget;

    public Account(){

    }

    public Account(int idAccount, Date dateCreation, double balance) {
        this.idAccount = idAccount;
        this.dateCreation = dateCreation;
        this.balance = balance;
    }

    public int getIdAccount() {return idAccount;}
    public void setIdAccount(Integer idAccount) {this.idAccount = idAccount;}
    public Date getDateCreation() {return dateCreation;}
    public void setDateCreation(Date dateCreation) {this.dateCreation = dateCreation;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
    public List<Transaction> getTransactionsSource() {return transactionsSource;}
    public void setTransactionsSource(List<Transaction> transactionsSource) {this.transactionsSource = transactionsSource;}
    public List<Transaction> getTransactionTarget() {return transactionTarget;}
    public void setTransactionTarget(List<Transaction> transactionTarget) {this.transactionTarget = transactionTarget;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return idAccount == account.idAccount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount);
    }
}
