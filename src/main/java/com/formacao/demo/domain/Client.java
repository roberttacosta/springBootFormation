package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;
    private String name;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCreation;

    @OneToOne
    @JoinColumn (name="idAccount")
    private Account account;

    public Client(){

    }

    public Client(int idClient, String name, String cpf, Date dateCreation, Account account) {
        this.idClient = idClient;
        this.name = name;
        this.cpf = cpf;
        this.dateCreation = dateCreation;
        this.account = account;
    }

    public int getIdClient() {return idClient;}
    public void setIdClient(Integer idClient) {this.idClient = idClient;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf; }
    public Date getDateCreation() {return dateCreation;}
    public void setDateCreation(Date dateCreation) {this.dateCreation = dateCreation;}
    public Account getAccount() {return account;}
    public void setAccount(Account account) {this.account = account;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return idClient == client.idClient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient);
    }
}
