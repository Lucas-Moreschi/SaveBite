package br.com.savebite.entity;

public class IngredienteReceita {

    private String nomeIngrediente; // Nome do ingrediente
    private Double quantidade;      // Quantidade do ingrediente
    private String medida;          // Unidade de medida (ex.: "g", "ml")

    public IngredienteReceita() {
    }

    public IngredienteReceita(String nomeIngrediente, Double quantidade, String medida) {
        this.nomeIngrediente = nomeIngrediente;
        this.quantidade = quantidade;
        this.medida = medida;
    }

    // Getters e setters
    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
