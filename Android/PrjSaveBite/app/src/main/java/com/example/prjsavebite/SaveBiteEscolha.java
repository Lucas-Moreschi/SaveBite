package com.example.prjsavebite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SaveBiteEscolha extends AppCompatActivity {
    private int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_bite_escolha);

        // Recupera o nome do usuário do SharedPreferences, se disponível
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String nomeUsuario = sharedPreferences.getString("username", "Visitante");
        usuarioId = sharedPreferences.getInt("usuario_id", -1); // Recupera o usuarioId

        TextView txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        txtNomeUsuario.setText(getString(R.string.usuario_nome, nomeUsuario));

        // Configuração dos botões
        Button btnCriarRec = findViewById(R.id.btnCriarRec);
        Button btnVerRec = findViewById(R.id.btnVerRec);
        Button btnMinhasRec = findViewById(R.id.btnMinhasRec);
        Button btnSair = findViewById(R.id.btnSair);

        // Configura o clique no botão Sair para realizar logout
        btnSair.setOnClickListener(v -> logout());

        // Configura o clique no botão Criar Receita
        btnCriarRec.setOnClickListener(v -> {
            Intent intent = new Intent(SaveBiteEscolha.this, ListaCriacao.class);
            startActivity(intent);
            finish();
        });

        // Configura o clique no botão Ver Receitas
        btnVerRec.setOnClickListener(v -> {
            Intent intent = new Intent(SaveBiteEscolha.this, ListaDeReceitas.class);
            intent.putExtra("usuario_id", usuarioId);  // Passa o usuarioId para a próxima Activity
            startActivity(intent);
        });

        // Configura o clique no botão Minhas Receitas
        btnMinhasRec.setOnClickListener(v -> {
            Intent intent = new Intent(SaveBiteEscolha.this, MinhasReceitas.class);
            intent.putExtra("usuario_id", usuarioId); // Passa o usuarioId para a próxima Activity
            startActivity(intent);
        });
    }

    public void logout() {
        // Limpa dados específicos da sessão no SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Remove dados de sessão: nome de usuário e ID do usuário
        editor.remove("username");
        editor.remove("userId");
        editor.apply(); // Aplica as alterações

        // Limpa os dados da classe SessaoUsuario (se estiver usando)
        SessaoUsuario.getInstance().clearUserData();

        Toast.makeText(getApplicationContext(), "Até a próxima!", Toast.LENGTH_SHORT).show();

        // Redireciona o usuário para a tela de login (MainActivity)
        Intent intent = new Intent(SaveBiteEscolha.this, MainActivity.class);
        startActivity(intent);
        finish(); // Encerra a Activity atual para evitar o retorno com o botão de voltar
    }
}