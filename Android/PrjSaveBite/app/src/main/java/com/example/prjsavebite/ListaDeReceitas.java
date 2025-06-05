package com.example.prjsavebite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListaDeReceitas extends AppCompatActivity {

    private ListView listViewReceitas;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaReceitas;
    private Acessa acessa; // Usando a classe Acessa para conexão com o banco
    private EditText edtPesquisaIngrediente;
    private ImageButton btnPesquisarIngredientes;
    private ImageButton btnPesquisarNomeReceita;
    private EditText edtPesquisaNomeReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_receitas);

        listViewReceitas = findViewById(R.id.listVReceitas);
        edtPesquisaIngrediente = findViewById(R.id.edtPesquisaIngrediente);
        btnPesquisarIngredientes = findViewById(R.id.btnPesquisarIngredientes);
        edtPesquisaNomeReceita = findViewById(R.id.edtPesquisaNomeReceita);
        btnPesquisarNomeReceita = findViewById(R.id.btnPesquisarNomeReceita);

        listaReceitas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReceitas);
        listViewReceitas.setAdapter(adapter);

        Button btnVoltarMenu = findViewById(R.id.btnVoltarMenu);
        Button btnDesfazerPesquisa = findViewById(R.id.btnDesfazerPesquisa);

        // Carrega todas as receitas ao abrir a tela
        carregarTodasReceitas();

        // Configura o clique na receita para exibir detalhes
        listViewReceitas.setOnItemClickListener((parent, view, position, id) -> {
            String receitaSelecionada = listaReceitas.get(position);
            Intent intent = new Intent(ListaDeReceitas.this, ReceitaSelecionada.class);
            intent.putExtra("nome_receita", receitaSelecionada);
            startActivity(intent);
        });

        btnVoltarMenu.setOnClickListener(v -> {
            Intent intent = new Intent(ListaDeReceitas.this, SaveBiteEscolha.class);
            startActivity(intent);
            finish();
        });



        // Configura o clique no botão de pesquisa para buscar receitas com o ingrediente especificado
        btnPesquisarIngredientes.setOnClickListener(v -> {
            String ingrediente = edtPesquisaIngrediente.getText().toString().trim();
            if (!ingrediente.isEmpty()) {
                pesquisarReceitasPorIngrediente(ingrediente);
            } else {
                Toast.makeText(this, "Digite um ingrediente para pesquisar.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura o clique no botão de pesquisa por nome de receita
        btnPesquisarNomeReceita.setOnClickListener(v -> {
            String nomeReceita = edtPesquisaNomeReceita.getText().toString().trim();
            if (!nomeReceita.isEmpty()) {
                pesquisarReceitasPorNome(nomeReceita);
            } else {
                Toast.makeText(this, "Digite o nome de uma receita para pesquisar.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDesfazerPesquisa.setOnClickListener(v -> {
            carregarTodasReceitas();
        });
    }

    public void carregarTodasReceitas() {
        acessa = new Acessa();

        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                String query = "SELECT Nome FROM Receitas";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                listaReceitas.clear();
                while (rs.next()) {
                    String nomeReceita = rs.getString("Nome");
                    listaReceitas.add(nomeReceita);
                }

                rs.close();
                stmt.close();
                con.close();

                // Atualiza o adapter para refletir os dados carregados
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Erro ao conectar ao banco de dados", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void pesquisarReceitasPorIngrediente(String ingrediente) {
        acessa = new Acessa();

        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                String query = "SELECT r.Nome " +
                        "FROM Receitas r " +
                        "JOIN Ingredientes_Receitas ir ON r.Id = ir.ReceitaId " +
                        "JOIN Ingredientes i ON ir.IngredienteId = i.Id " +
                        "WHERE i.Nome LIKE ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, "%" + ingrediente + "%");
                ResultSet rs = stmt.executeQuery();

                listaReceitas.clear();
                while (rs.next()) {
                    String nomeReceita = rs.getString("Nome");
                    listaReceitas.add(nomeReceita);
                }

                rs.close();
                stmt.close();
                con.close();

                // Atualiza o adapter para refletir os dados carregados
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Erro ao conectar ao banco de dados", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Método para pesquisar receitas por nome
    public void pesquisarReceitasPorNome(String nomeReceita) {
        acessa = new Acessa();

        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                // Consulta SQL para pesquisar receitas pelo nome
                String query = "SELECT Nome FROM Receitas WHERE Nome LIKE ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, "%" + nomeReceita + "%");  // Usando o operador LIKE para busca parcial
                ResultSet rs = stmt.executeQuery();

                // Limpa a lista de receitas antes de adicionar os resultados
                listaReceitas.clear();
                while (rs.next()) {
                    String nome = rs.getString("Nome");
                    listaReceitas.add(nome);  // Adiciona o nome da receita à lista
                }

                rs.close();
                stmt.close();
                con.close();

                // Atualiza o adapter
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Erro ao conectar ao banco de dados", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}