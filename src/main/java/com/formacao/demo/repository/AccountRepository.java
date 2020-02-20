package com.formacao.demo.repository;

import com.formacao.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer> {

}
