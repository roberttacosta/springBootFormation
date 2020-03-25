package com.formacao.demo.dto;

public class CredentialsDTO {

    private String cpf;
    private String password;

    public CredentialsDTO() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
