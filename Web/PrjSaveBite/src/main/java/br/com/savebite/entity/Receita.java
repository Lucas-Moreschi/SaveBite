package br.com.savebite.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Receita {

    private Long id;
    private String nome;
    private String descricao;
    private Integer tempoPreparo;
    private Integer porcoes;
    private Long categoriaId;
    private String dificuldade;
    private Long usuarioId;
    private Double mediaAvaliacao;
    private java.sql.Date dataCriacao;
    private List<Instrucoes> instrucoes; // Lista de instruções associadas à receita
    private List<IngredienteReceita> ingredientes; // Lista de ingredientes associados à receita

    // Construtor padrão
    public Receita() {
        this.instrucoes = new ArrayList<>();
        this.ingredientes = new ArrayList<>();
    }

    // Construtor completo
    public Receita(Long id, String nome, String descricao, Integer tempoPreparo, Integer porcoes, Long categoriaId,
            String dificuldade, Long usuarioId, Double mediaAvaliacao, Date dataCriacao,
            List<Instrucoes> instrucoes, List<IngredienteReceita> ingredientes) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tempoPreparo = tempoPreparo;
        this.porcoes = porcoes;
        this.categoriaId = categoriaId;
        this.dificuldade = dificuldade;
        this.usuarioId = usuarioId;
        this.mediaAvaliacao = mediaAvaliacao;
        this.dataCriacao = dataCriacao;
        this.instrucoes = instrucoes == null ? new ArrayList<>() : instrucoes;
        this.ingredientes = ingredientes == null ? new ArrayList<>() : ingredientes;
    }

    // Construtor sem a lista de instruções e ingredientes
    public Receita(Long id, String nome, String descricao, Integer tempoPreparo, Integer porcoes, Long categoriaId,
            String dificuldade, Long usuarioId, Double mediaAvaliacao, Date dataCriacao) {
        this(id, nome, descricao, tempoPreparo, porcoes, categoriaId, dificuldade, usuarioId, mediaAvaliacao, dataCriacao, null, null);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(Integer tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    public Integer getPorcoes() {
        return porcoes;
    }

    public void setPorcoes(Integer porcoes) {
        this.porcoes = porcoes;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public java.sql.Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(java.sql.Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Instrucoes> getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(List<Instrucoes> instrucoes) {
        this.instrucoes = instrucoes;
    }

    public List<IngredienteReceita> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceita> ingredientes) {
        this.ingredientes = ingredientes;
    }
}