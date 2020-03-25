package com.formacao.demo.dto;

import com.formacao.demo.domain.enums.TypeTransaction;

public class TransactionDTO {

    private Integer idTargetAccount;
    private double transactionAmount;
    private TypeTransaction typeTransaction;

    public TransactionDTO(Integer idSourceAccount, Integer idTargetAccount, double transactionAmount, TypeTransaction typeTransaction) {
        this.idTargetAccount = idTargetAccount;
        this.transactionAmount = transactionAmount;
        this.typeTransaction = typeTransaction;
    }

    public TransactionDTO() {

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
