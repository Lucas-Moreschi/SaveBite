package com.example.prjsavebite;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReceitaSelecionada extends AppCompatActivity {

    private TextView txtTituloReceita;
    private TextView txtDescricaoReceita;
    private TextView txtPassoAPasso;
    private TextView txtTempoPreparo;
    private TextView txtPorcoes;
    private TextView txtDificuldade;
    private TextView txtMediaAvaliacao;
    private TextView txtDataCriacao;
    private ListView listViewIngredientes;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaIngredientes;
    private Acessa acessa;
    private EditText edtAvaliacoes;

    // Defina nomeReceita como um campo da classe
    private String nomeReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita_selecionada);

        // Inicializa os elementos de interface
        txtTituloReceita = findViewById(R.id.txtTituloReceita);
        txtDescricaoReceita = findViewById(R.id.txtDescricaoReceita);
        txtPassoAPasso = findViewById(R.id.txtPassosReceita);
        txtTempoPreparo = findViewById(R.id.txtTempoPreparo);
        txtPorcoes = findViewById(R.id.txtPorcoes);
        txtDificuldade = findViewById(R.id.txtDificuldade);
        txtMediaAvaliacao = findViewById(R.id.txtMediaAvaliacao);
        txtDataCriacao = findViewById(R.id.txtDataCriacao);
        listViewIngredientes = findViewById(R.id.listVIngredientesReceita);

        edtAvaliacoes = findViewById(R.id.edtAvaliacoes);
        Button btnEnviarAvaliacao = findViewById(R.id.btnEnviarAvaliacao);

        listaIngredientes = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaIngredientes);
        listViewIngredientes.setAdapter(adapter);

        // Inicializa o campo nomeReceita com o valor da Intent
        nomeReceita = getIntent().getStringExtra("nome_receita");
        carregarDetalhesReceita(nomeReceita);

        // Define o OnClickListener para enviar avaliação
        btnEnviarAvaliacao.setOnClickListener(this::enviarAvaliacao);

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarDetalhesReceita(String nomeReceita) {
        acessa = new Acessa();

        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                // Query principal para buscar informações da receita
                String query = "SELECT r.Id, r.Nome, r.Descricao, r.TempoPreparo, r.Porcoes, r.Dificuldade, " +
                        "r.MediaAvaliacao, r.DataCriacao, i.Nome AS Ingrediente, ir.Quantidade, m.Unidade AS Medida " +
                        "FROM Receitas r " +
                        "LEFT JOIN Ingredientes_Receitas ir ON r.Id = ir.ReceitaId " +
                        "LEFT JOIN Ingredientes i ON ir.IngredienteId = i.Id " +
                        "LEFT JOIN Medidas m ON ir.MedidaId = m.Id " +
                        "WHERE r.Nome = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, nomeReceita);
                ResultSet rs = stmt.executeQuery();

                // Variáveis para armazenar os dados da receita
                int receitaId = -1; // Para armazenar o Id da receita
                String descricao = null, dificuldade = null;
                int tempoPreparo = 0, porcoes = 0;
                double mediaAvaliacao = 0;
                Date dataCriacao = null;
                ArrayList<String> ingredientes = new ArrayList<>();

                if (rs.next()) {
                    // Obtenção dos dados gerais da receita
                    receitaId = rs.getInt("Id"); // Captura o Id da receita
                    descricao = rs.getString("Descricao");
                    tempoPreparo = rs.getInt("TempoPreparo");
                    porcoes = rs.getInt("Porcoes");
                    dificuldade = rs.getString("Dificuldade");
                    mediaAvaliacao = rs.getDouble("MediaAvaliacao");
                    dataCriacao = rs.getDate("DataCriacao");

                    // Adiciona o primeiro ingrediente e continua a ler os demais
                    do {
                        String ingrediente = rs.getString("Ingrediente");
                        if (ingrediente != null) {
                            double quantidade = rs.getDouble("Quantidade");
                            String medida = rs.getString("Medida");
                            ingredientes.add(ingrediente + ": " + quantidade + " " + medida);
                        }
                    } while (rs.next());
                }

                // Busca as etapas do passo a passo usando o Id da receita
                String queryEtapas = "SELECT Descricao FROM Instrucoes WHERE ReceitaId = ? ORDER BY Etapa";
                PreparedStatement stmtEtapas = con.prepareStatement(queryEtapas);
                stmtEtapas.setInt(1, receitaId); // Usa o Id da receita para evitar problemas de subquery
                ResultSet rsEtapas = stmtEtapas.executeQuery();

                //Construtor
                StringBuilder passoAPassoBuilder = new StringBuilder();
                int numeroEtapa = 1; // Inicia o número da etapa em 1
                while (rsEtapas.next()) {
                    String descricaoEtapa = rsEtapas.getString("Descricao");
                    passoAPassoBuilder.append(numeroEtapa).append(". ").append(descricaoEtapa).append("\n");
                    numeroEtapa++; // Incrementa o número da etapa
                }
                String passoAPasso = passoAPassoBuilder.toString().trim();

                // Formata a data antes de exibir
                String dataCriacaoFormatada = (dataCriacao != null)
                        ? new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dataCriacao)
                        : "Não informado";


                txtTituloReceita.setText(nomeReceita);
                txtDescricaoReceita.setText(descricao != null ? "Descrição: " + descricao : "Descrição não disponível");
                txtTempoPreparo.setText("Tempo de preparo: " + (tempoPreparo > 0 ? tempoPreparo + " min" : "Não informado"));
                txtPorcoes.setText("Porções: " + (porcoes > 0 ? porcoes : "Não informado"));
                txtDificuldade.setText("Dificuldade: " + (dificuldade != null ? dificuldade : "Não informado"));
                txtMediaAvaliacao.setText("Avaliação média: " + (mediaAvaliacao > 0 ? mediaAvaliacao : "Sem avaliações"));
                txtDataCriacao.setText("Criado em: " + dataCriacaoFormatada);
                txtPassoAPasso.setText(!passoAPasso.isEmpty() ? passoAPasso : "Passo a passo não disponível");

                // Atualiza a lista de ingredientes
                listaIngredientes.clear();
                listaIngredientes.addAll(ingredientes);
                adapter.notifyDataSetChanged();


                rs.close();
                stmt.close();
                rsEtapas.close();
                stmtEtapas.close();
                con.close();
            } else {
                Toast.makeText(this, "Erro ao conectar ao banco de dados", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("ReceitaSelecionada", "Erro ao carregar detalhes da receita", e);
            Toast.makeText(this, "Erro ao carregar dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    public void enviarAvaliacao(View view) {
        String avaliacaoStr = edtAvaliacoes.getText().toString();

        if (avaliacaoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, insira uma avaliação.", Toast.LENGTH_SHORT).show();
            return;
        }

        double avaliacao;
        try {
            avaliacao = Double.parseDouble(avaliacaoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "A avaliação deve ser um número válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (avaliacao < 1 || avaliacao > 5) {
            Toast.makeText(this, "A avaliação deve ser entre 1 e 5.", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = SessaoUsuario.getInstance().getUserId();

        if (userId == -1) {
            Toast.makeText(this, "Usuário não encontrado. Faça login novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                String query = "INSERT INTO Avaliacoes (ReceitaId, UsuarioId, Pontuacao, Data) " +
                        "SELECT r.Id, ?, ?, GETDATE() " +
                        "FROM Receitas r " +
                        "WHERE r.Nome = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, userId);
                stmt.setDouble(2, avaliacao);
                stmt.setString(3, nomeReceita);
                stmt.executeUpdate();

                atualizarMediaAvaliacao(nomeReceita);

                stmt.close();
                con.close();
                Toast.makeText(this, "Avaliação enviada com sucesso.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ReceitaSelecionada", "Erro ao enviar avaliação", e);
            Toast.makeText(this, "Erro ao enviar avaliação: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void atualizarMediaAvaliacao(String nomeReceita) {
        try {
            Connection con = acessa.entBanco(this);
            if (con != null) {
                String query = "SELECT AVG(Pontuacao) AS MediaAvaliacao " +
                        "FROM Avaliacoes a " +
                        "JOIN Receitas r ON a.ReceitaId = r.Id " +
                        "WHERE r.Nome = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, nomeReceita);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double mediaAvaliacao = rs.getDouble("MediaAvaliacao");
                    txtMediaAvaliacao.setText(getString(R.string.avaliacao_media, mediaAvaliacao));
                }

                rs.close();
                stmt.close();
                con.close();
            }
        } catch (Exception e) {
            Log.e("ReceitaSelecionada", "Erro ao calcular média de avaliação", e);
            Toast.makeText(this, "Erro ao calcular média de avaliação: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}