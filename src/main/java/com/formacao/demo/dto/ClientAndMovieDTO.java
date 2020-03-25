package com.formacao.demo.dto;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class ClientAndMovieDTO {

    @NotEmpty(message = "Preenchimento obrigatório")
    private String title;

    public ClientAndMovieDTO(@NotEmpty(message = "Preenchimento obrigatório") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
