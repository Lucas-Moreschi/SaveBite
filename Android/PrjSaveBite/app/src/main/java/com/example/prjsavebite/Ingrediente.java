package com.example.prjsavebite;

import java.math.BigDecimal;

public class Ingrediente {
    private String nome;
    private BigDecimal quantidade;
    private String unidade;

    public Ingrediente(String nome, BigDecimal quantidade, String unidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public String getUnidade() {
        return unidade;
    }
}



