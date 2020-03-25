package com.formacao.demo.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ClientNewDTO {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @CPF
    @Column(unique = true)
    private String cpf;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    private double balance;

    public ClientNewDTO() {

    }

    public ClientNewDTO(@NotEmpty(message = "Preenchimento obrigatório") @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres") String name, @NotEmpty(message = "Preenchimento obrigatório") @CPF String cpf, String password, @NotEmpty(message = "Preenchimento obrigatório") String email, double balance) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
