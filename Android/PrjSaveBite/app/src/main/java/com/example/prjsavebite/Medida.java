package com.example.prjsavebite;

public class Medida {
    private String nome;
    private String abreviacao;

    public Medida(String nome, String abreviacao) {
        this.nome = nome;
        this.abreviacao = abreviacao;
    }

    public String getNome() {
        return nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }
}
