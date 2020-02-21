package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;
    @NotEmpty (message = "Preenchimento obrigatório")
    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateCreation;
    @JsonIgnore
    @OneToOne
    @JoinColumn (name="id_account")
    private Account account;

    public Client(){

    }

    public Client(Integer id, String name, String cpf, Date dateCreation, Account account) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.dateCreation = dateCreation;
        this.account = account;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
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
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
