package com.example.prjsavebite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SaveBiteCadastro extends AppCompatActivity {

    EditText NomeCad, EmailCad, SenhaCad, ConfSenhaCad;
    Acessa objA = new Acessa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_bite_cadastro);

        NomeCad = findViewById(R.id.editNomeCompleto);
        EmailCad = findViewById(R.id.editEmail);
        SenhaCad = findViewById(R.id.editSenha);
        ConfSenhaCad = findViewById(R.id.editConfSenha);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastro = new Intent(SaveBiteCadastro.this, MainActivity.class);
                startActivity(intentCadastro);
                finish();
            }
        });
    }

    public void Cadastrar(View v) {
        String nome = NomeCad.getText().toString().trim();
        String email = EmailCad.getText().toString().trim();
        String senha = SenhaCad.getText().toString().trim();
        String confirmacaoSenha = ConfSenhaCad.getText().toString().trim();

        // Validação dos campos
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmacaoSenha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmacaoSenha)) {
            Toast.makeText(getApplicationContext(), "A confirmação da senha não coincide com a senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Geração do hash da senha para armazenamento seguro
        String senhaHash;
        try {
            senhaHash = hashSenha(senha);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao processar a senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Conectar ao banco de dados e inserir os dados
        objA.entBanco(this);
        try (Connection conn = objA.entBanco(this);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Usuarios (Nome, Email, SenhaHash, TipoUsuario) VALUES (?, ?, ?, ?)")) {

            // Definindo os valores para o PreparedStatement
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, senhaHash);
            pstmt.setString(4, "usuario");

            int res = pstmt.executeUpdate();
            if (res != 0) {
                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();

                // Limpar os campos após cadastro bem-sucedido
                NomeCad.setText("");
                EmailCad.setText("");
                SenhaCad.setText("");
                ConfSenhaCad.setText("");

                // Redirecionar diretamente para a SaveBiteEscolha
                Intent intentEscolha = new Intent(SaveBiteCadastro.this, MainActivity.class);
                intentEscolha.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpa o histórico
                startActivity(intentEscolha);
            } else {
                Toast.makeText(getApplicationContext(), "Falha ao salvar os dados.", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException ex) {
            Toast.makeText(getApplicationContext(), "Erro ao salvar no banco de dados: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
    // Método para gerar hash da senha (usando SHA-256 para este exemplo)
    private String hashSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}