package br.com.savebite.entity;

public class Instrucoes {

    private Long id;
    private Integer etapa;
    private String descricao;
    private Receita receita;

    // Construtores
    public Instrucoes() {
    }

    public Instrucoes(Long id, Integer etapa, String descricao, Receita receita) {
        this.id = id;
        this.etapa = etapa;
        this.descricao = descricao;
        this.receita = receita;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtapa() {
        return etapa;
    }

    public void setEtapa(Integer etapa) {
        this.etapa = etapa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }
}
