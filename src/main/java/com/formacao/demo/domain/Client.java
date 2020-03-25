package com.formacao.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.formacao.demo.domain.enums.Profile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Client extends RepresentationModel<Client> {

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

    @NotEmpty(message = "Preenchimento obrigatório")
    @Column(unique = true)
    @Email
    private String email;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROFILES")
    private Set<Integer> profiles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "id_account")
    private Account account;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "MOVIE_CLIENT", joinColumns = @JoinColumn(name = "name_client"), inverseJoinColumns = @JoinColumn(name = "name_movie"))
    private List<OMDB> omdbs = new ArrayList<>();

    public Client() {
        addProfile(Profile.ROLE_CLIENT);
    }

    public Client(Integer id, String name, String cpf, String email, String password, Account account) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.account = account;
        addProfile(Profile.ROLE_CLIENT);
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

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
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
