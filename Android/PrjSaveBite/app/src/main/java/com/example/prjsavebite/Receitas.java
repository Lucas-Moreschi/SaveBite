package com.example.prjsavebite;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Receitas implements Serializable {  // Implementando Serializable para enviar via Intent
    private final String titulo;
    private final ArrayList<String> ingredientes;
    private final String dataCriacao; // Atributo para armazenar a data de criação

    // Construtor
    public Receitas(String titulo, ArrayList<String> ingredientes) {
        this.titulo = titulo;
        this.ingredientes = ingredientes;

        // Captura a data atual no formato dia/mês/ano
        this.dataCriacao = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }
}
