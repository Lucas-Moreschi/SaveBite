package com.example.prjsavebite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListaCriacao extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtNomeIng;
    private EditText txtQtd;
    private ListView ListVIng;
    private EditText txtDescricaoRec;
    private EditText txtTempoPreparo;
    private EditText txtPorcoes;
    private Spinner spinnerDificuldade;
    private Button btnSair2;
    private Button btnAddIng;
    private Spinner spinnerMedida;

    private ArrayList<Ingrediente> ingredientesList;
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList<String> ingredientsDisplayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_criacao);

        // Inicialização das Views
        txtTitulo = findViewById(R.id.editTituloRec);
        txtNomeIng = findViewById(R.id.editIngrediente);
        txtQtd = findViewById(R.id.txtQtd);
        ListVIng = findViewById(R.id.ListViewIngredientes);
        txtDescricaoRec = findViewById(R.id.editDescricaoRec);
        txtTempoPreparo = findViewById(R.id.editTempoPreparo);
        txtPorcoes = findViewById(R.id.editPorcoes);
        spinnerDificuldade = findViewById(R.id.spinnerDificuldade);
        btnSair2 = findViewById(R.id.btnSair2);
        btnAddIng = findViewById(R.id.btnAdd);
        spinnerMedida = findViewById(R.id.spnMedida);

        ingredientesList = new ArrayList<>();
        ingredientsDisplayList = new ArrayList<>();
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientsDisplayList);
        ListVIng.setAdapter(listViewAdapter);

        // Configuração do OnItemClickListener
        ListVIng.setOnItemClickListener((parent, view, position, id) -> {
            // Remover o ingrediente da lista
            ingredientsDisplayList.remove(position);
            ingredientesList.remove(position);
            listViewAdapter.notifyDataSetChanged();
            ajustarAlturaListView(ListVIng); // Atualizar altura ao remover itens

            // Se a lista de exibição estiver vazia, esconda o ListView
            if (ingredientsDisplayList.isEmpty()) {
                ListVIng.setVisibility(View.GONE);
            } else {
                ListVIng.setVisibility(View.VISIBLE);
            }
        });

        // Configuração do Spinner para medida
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.medidas_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedida.setAdapter(spinnerAdapter);

        // Configuração do Spinner para dificuldade
        ArrayAdapter<CharSequence> dificuldadeAdapter = ArrayAdapter.createFromResource(this,
                R.array.dificuldades, android.R.layout.simple_spinner_item);
        dificuldadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDificuldade.setAdapter(dificuldadeAdapter);

        // Configuração do botão de adicionar ingrediente
        btnAddIng.setOnClickListener(this::adicionarIngrediente);

        // Configuração do botão de sair
        btnSair2.setOnClickListener(v -> {
            Intent intent = new Intent(ListaCriacao.this, SaveBiteEscolha.class);
            startActivity(intent);
            finish();
        });
    }

    public void adicionarIngrediente(View view) {
        // Obtém os dados dos campos de entrada
        String ingrediente = txtNomeIng.getText().toString();
        String quantidade = txtQtd.getText().toString();
        String medida = spinnerMedida.getSelectedItem().toString();

        // Verifica se os campos não estão vazios
        if (!ingrediente.isEmpty() && !quantidade.isEmpty()) {
            // Tenta converter a quantidade para BigDecimal
            BigDecimal quantidadeBigDecimal;
            try {
                quantidadeBigDecimal = new BigDecimal(quantidade);  // Converte a quantidade de String para BigDecimal
            } catch (NumberFormatException e) {
                // Em caso de erro na conversão, mostra uma mensagem de erro
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Formata o texto do ingrediente para exibição
            String ingredienteCompleto = ingrediente + " - " + quantidade + " " + medida;

            // Adiciona o novo ingrediente à lista existente
            ingredientsDisplayList.add(ingredienteCompleto); // Adiciona à lista de exibição
            ingredientesList.add(new Ingrediente(ingrediente, quantidadeBigDecimal, medida)); // Passa o BigDecimal para o construtor

            listViewAdapter.notifyDataSetChanged(); // Notifica que os dados da lista foram atualizados
            ajustarAlturaListView(ListVIng); // Atualizar a altura da ListView

            // Torna a ListView visível
            ListVIng.setVisibility(View.VISIBLE);

            // Limpa os campos após adicionar
            txtNomeIng.setText("");
            txtQtd.setText("");
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void ajustarAlturaListView(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.UNSPECIFIED
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



    private int adicionarOuBuscarMedida(Connection conn, String unidade) throws SQLException {
        String sqlBusca = "SELECT Id FROM Medidas WHERE Unidade = ?";
        try (PreparedStatement pstmtBusca = conn.prepareStatement(sqlBusca)) {
            pstmtBusca.setString(1, unidade);
            try (ResultSet rs = pstmtBusca.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Id");
                }
            }
        }

        String sqlInsercao = "INSERT INTO Medidas (Unidade) VALUES (?)";
        try (PreparedStatement pstmtInsercao = conn.prepareStatement(sqlInsercao, Statement.RETURN_GENERATED_KEYS)) {
            pstmtInsercao.setString(1, unidade);
            pstmtInsercao.executeUpdate();
            try (ResultSet generatedKeys = pstmtInsercao.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("Erro ao inserir a unidade de medida: " + unidade);
    }

    private int adicionarOuBuscarIngrediente(Connection conn, String nomeIngrediente) throws SQLException {
        String sqlBusca = "SELECT Id FROM Ingredientes WHERE Nome = ?";
        try (PreparedStatement pstmtBusca = conn.prepareStatement(sqlBusca)) {
            pstmtBusca.setString(1, nomeIngrediente);
            try (ResultSet rs = pstmtBusca.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Id");
                }
            }
        }

        String sqlInsercao = "INSERT INTO Ingredientes (Nome) VALUES (?)";
        try (PreparedStatement pstmtInsercao = conn.prepareStatement(sqlInsercao, Statement.RETURN_GENERATED_KEYS)) {
            pstmtInsercao.setString(1, nomeIngrediente);
            pstmtInsercao.executeUpdate();
            try (ResultSet generatedKeys = pstmtInsercao.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        throw new SQLException("Erro ao inserir o ingrediente: " + nomeIngrediente);
    }

    public void salvarReceita(View view) {
        String titulo = txtTitulo.getText().toString();
        String descricao = txtDescricaoRec.getText().toString();
        String passoAPasso = ((EditText) findViewById(R.id.editPassoAPasso)).getText().toString().trim();
        String tempoPreparoStr = txtTempoPreparo.getText().toString();
        String porcoesStr = txtPorcoes.getText().toString();
        String dificuldade = spinnerDificuldade.getSelectedItem().toString();

        if (titulo.isEmpty()) {
            Toast.makeText(this, "O título da receita não pode ser vazio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ingredientesList.isEmpty()) {
            Toast.makeText(this, "Adicione ao menos um ingrediente.", Toast.LENGTH_SHORT).show();
            return;
        }

        int tempoPreparo = tempoPreparoStr.isEmpty() ? 0 : Integer.parseInt(tempoPreparoStr);
        int porcoes = porcoesStr.isEmpty() ? 0 : Integer.parseInt(porcoesStr);

        // Obtém a data atual no formato yyyy-MM-dd
        java.util.Date dataAtual = new java.util.Date();
        java.sql.Date dataCriacao = new java.sql.Date(dataAtual.getTime());

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        int usuarioId = sharedPreferences.getInt("userId", -1);

        if (usuarioId == -1) {
            Toast.makeText(this, "Erro: usuário não logado.", Toast.LENGTH_LONG).show();
            return;
        }

        Acessa objAcessa = new Acessa();
        try (Connection conn = objAcessa.entBanco(this);
             PreparedStatement pstmtReceita = conn.prepareStatement(
                     "INSERT INTO Receitas (Nome, Descricao, TempoPreparo, Porcoes, Dificuldade, UsuarioId, DataCriacao) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmtReceita.setString(1, titulo);
            pstmtReceita.setString(2, descricao);
            pstmtReceita.setInt(3, tempoPreparo);
            pstmtReceita.setInt(4, porcoes);
            pstmtReceita.setString(5, dificuldade);
            pstmtReceita.setInt(6, usuarioId);
            pstmtReceita.setDate(7, dataCriacao);

            pstmtReceita.executeUpdate();

            try (ResultSet generatedKeys = pstmtReceita.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int receitaId = generatedKeys.getInt(1);

                    // Insere os ingredientes associados à receita
                    String sqlIngredienteReceita = "INSERT INTO Ingredientes_Receitas (ReceitaId, IngredienteId, Quantidade, MedidaId, Unidade) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmtIngredienteReceita = conn.prepareStatement(sqlIngredienteReceita)) {
                        for (Ingrediente ingrediente : ingredientesList) {
                            int ingredienteId = adicionarOuBuscarIngrediente(conn, ingrediente.getNome());
                            int medidaId = adicionarOuBuscarMedida(conn, ingrediente.getUnidade());

                            pstmtIngredienteReceita.setInt(1, receitaId);
                            pstmtIngredienteReceita.setInt(2, ingredienteId);
                            pstmtIngredienteReceita.setBigDecimal(3, ingrediente.getQuantidade());
                            pstmtIngredienteReceita.setInt(4, medidaId);
                            pstmtIngredienteReceita.setString(5, ingrediente.getUnidade());
                            pstmtIngredienteReceita.executeUpdate();
                        }
                    }

                    // Insere as etapas (passo a passo) da receita
                    if (!passoAPasso.isEmpty()) {
                        String[] etapas = passoAPasso.split("\n");
                        String sqlEtapas = "INSERT INTO Instrucoes (ReceitaId, Etapa, Descricao) VALUES (?, ?, ?)";
                        try (PreparedStatement pstmtEtapas = conn.prepareStatement(sqlEtapas)) {
                            for (int i = 0; i < etapas.length; i++) {
                                String etapaDescricao = etapas[i].trim();
                                if (!etapaDescricao.isEmpty()) {
                                    pstmtEtapas.setInt(1, receitaId);
                                    pstmtEtapas.setInt(2, i + 1);
                                    pstmtEtapas.setString(3, etapaDescricao);
                                    pstmtEtapas.executeUpdate();
                                }
                            }
                        }
                    }

                    Toast.makeText(this, "Receita salva com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Erro ao salvar a receita.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao salvar no banco de dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
