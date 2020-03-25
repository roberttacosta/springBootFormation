package com.formacao.demo.domain.enums;

public enum Profile {
    ROLE_ADMIN (1, "ROLE_ADMIN"),
    ROLE_CLIENT (2, "ROLE_CLIENT");

    private int cod;
    private String descricao;

    private Profile(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return descricao;
    }

    public static Profile toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Profile x : Profile.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
