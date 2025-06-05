package com.example.prjsavebite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MinhasReceitas extends AppCompatActivity {

    private ListView listViewMinhasReceitas;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaMinhasReceitas;
    private Acessa dbHelper; // Inicializa o Acessa para conexão com o banco

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_receitas);

        listViewMinhasReceitas = findViewById(R.id.listMinhasReceitas);
        Button btnVoltarMenu = findViewById(R.id.btnVoltarMenu);
        listaMinhasReceitas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMinhasReceitas);
        listViewMinhasReceitas.setAdapter(adapter);

        // Inicializa o dbHelper
        dbHelper = new Acessa();

        carregarReceitas();

        // Ação ao clicar em uma receita da lista
        listViewMinhasReceitas.setOnItemClickListener((parent, view, position, id) -> {
            String receitaSelecionada = listaMinhasReceitas.get(position);
            Intent intent = new Intent(MinhasReceitas.this, ReceitaSelecionada.class);
            intent.putExtra("nome_receita", receitaSelecionada);
            startActivity(intent);
        });

        // Botão para voltar ao menu principal
        btnVoltarMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MinhasReceitas.this, SaveBiteEscolha.class);
            startActivity(intent);
            finish();
        });
    }

    private void carregarReceitas() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String usuarioLogado = sharedPreferences.getString("username", null);

        if (usuarioLogado != null) {
            // Estabelece conexão com o banco de dados
            Connection connection = dbHelper.entBanco(this);
            if (connection != null) {
                try {
                    // Altere a query para usar o nome correto da coluna
                    String query = "SELECT nome FROM Receitas WHERE UsuarioId = (SELECT Id FROM Usuarios WHERE Nome = ?)";

                    // Usa PreparedStatement para evitar SQL Injection
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, usuarioLogado);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    listaMinhasReceitas.clear();
                    int receitaCount = 0;

                    while (resultSet.next()) {
                        String nome = resultSet.getString("nome");
                        listaMinhasReceitas.add(nome);
                        receitaCount++;
                    }

                    Toast.makeText(this, "Você tem " + receitaCount + " receitas salvas.", Toast.LENGTH_SHORT).show();

                    resultSet.close();
                    preparedStatement.close();

                    // Notifica o adapter sobre as mudanças
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                } catch (SQLException e) {
                    Toast.makeText(this, "Erro ao carregar receitas: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    try {
                        if (!connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        Toast.makeText(this, "Erro ao fechar conexão: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, "Não foi possível conectar ao banco de dados.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Usuário não encontrado. Verifique o login.", Toast.LENGTH_LONG).show();
        }
    }
}