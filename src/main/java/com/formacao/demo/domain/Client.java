package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @CPF
    @Column(unique = true)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "MOVIE_CLIENT", joinColumns = @JoinColumn(name = "name_client"), inverseJoinColumns = @JoinColumn(name = "name_movie"))
    private List<OMDB> omdbs = new ArrayList<>();

    public Client() {

    }

    public Client(Integer id, String name, String cpf, Account account) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<OMDB> getOmdbs() {
        return omdbs;
    }

    public void setOmdbs(List<OMDB> omdbs) {
        this.omdbs = omdbs;
    }

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
