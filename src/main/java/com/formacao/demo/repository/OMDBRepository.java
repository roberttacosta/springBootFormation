package com.formacao.demo.repository;

import com.formacao.demo.domain.OMDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OMDBRepository extends JpaRepository<OMDB, String> {
    OMDB findByTitle(String title);
}
