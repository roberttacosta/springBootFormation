package com.formacao.demo.domain.enums;

public enum TypeTransaction {
    WITHDRAW (1, "Withdraw"),
    DEPOSIT (2, "Deposit"),
    TRANSER (3, "Transfer"),
    RECEIVETRANSFER (4, "Receive Transfer");

    private int codeType;
    private String descricrao;

    TypeTransaction(int codeType, String descricrao) {
        this.codeType = codeType;
        this.descricrao = descricrao;
    }

    public int getCodeType() {return codeType;}
    public String getDescricrao() {return descricrao;}

    public static TypeTransaction toEnum(Integer cod){
        if (cod == null){
            return null;
        }
        for (TypeTransaction x : TypeTransaction.values()){
            if(cod.equals(x.getCodeType())){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: "+cod);
    }
}
