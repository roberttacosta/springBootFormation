package com.formacao.demo.dto;

import com.formacao.demo.domain.enums.TypeTransaction;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TransactionDTO {

    private Integer idSourceAccount;
    private Integer idTargetAccount;
    private double transactionAmount;
    private TypeTransaction typeTransaction;

    public TransactionDTO(Integer idSourceAccount, Integer idTargetAccount, double transactionAmount, TypeTransaction typeTransaction) {
        this.idSourceAccount = idSourceAccount;
        this.idTargetAccount = idTargetAccount;
        this.transactionAmount = transactionAmount;
        this.typeTransaction = typeTransaction;
    }

    public TransactionDTO() {

    }

    public Integer getIdSourceAccount() {
        return idSourceAccount;
    }

    public void setIdSourceAccount(Integer idSourceAccount) {
        this.idSourceAccount = idSourceAccount;
    }

    public Integer getIdTargetAccount() {
        return idTargetAccount;
    }

    public void setIdTargetAccount(Integer idTargetAccount) {
        this.idTargetAccount = idTargetAccount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }
}
