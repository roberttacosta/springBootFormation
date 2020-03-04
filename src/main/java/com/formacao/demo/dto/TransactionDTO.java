package com.formacao.demo.dto;

import com.formacao.demo.domain.enums.TypeTransaction;

public class TransactionDTO {

    private int idSourceAccount;
    private int idTargetAccount;
    private double transactionAmount;
    private TypeTransaction typeTransaction;

    public TransactionDTO(int idSourceAccount, int idTargetAccount, double transactionAmount, TypeTransaction typeTransaction) {
        this.idSourceAccount = idSourceAccount;
        this.idTargetAccount = idTargetAccount;
        this.transactionAmount = transactionAmount;
        this.typeTransaction = typeTransaction;
    }

    public TransactionDTO() {

    }

    public int getIdSourceAccount() {
        return idSourceAccount;
    }

    public void setIdSourceAccount(int idSourceAccount) {
        this.idSourceAccount = idSourceAccount;
    }

    public int getIdTargetAccount() {
        return idTargetAccount;
    }

    public void setIdTargetAccount(int idTargetAccount) {
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
