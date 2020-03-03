package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dateCreation;

    private double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> transactionsSource;

    @JsonIgnore
    @OneToMany(mappedBy = "targetAccount")
    private List<Transaction> transactionTarget;

    public Account() {

    }

    public Account(Integer id, LocalDateTime dateCreation, double balance) {
        this.id = id;
        this.dateCreation = dateCreation;
        this.balance = balance;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionsSource() {
        return transactionsSource;
    }

    public void setTransactionsSource(List<Transaction> transactionsSource) {
        this.transactionsSource = transactionsSource;
    }

    public List<Transaction> getTransactionTarget() {
        return transactionTarget;
    }

    public void setTransactionTarget(List<Transaction> transactionTarget) {
        this.transactionTarget = transactionTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
