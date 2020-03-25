package com.formacao.demo.repository;

import com.formacao.demo.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByCpf(String cpf);
    Client findByEmail(String email);
}
