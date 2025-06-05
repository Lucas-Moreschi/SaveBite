package com.example.prjsavebite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    EditText usuario, senha;
    Acessa objA = new Acessa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = findViewById(R.id.editNomeLogin);
        senha = findViewById(R.id.editSenhaLogin);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SaveBiteCadastro.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void entrar(View v) {
        String email = usuario.getText().toString().trim();
        String senhaUsuario = senha.getText().toString().trim();

        // Verifica se os campos estão preenchidos
        if (email.isEmpty() || senhaUsuario.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicia a conexão com o banco de dados
        Connection conn = objA.entBanco(this);
        if (conn == null) {
            Log.e("LoginError", "Erro ao estabelecer conexão com o banco de dados");
            Toast.makeText(getApplicationContext(), "Erro ao conectar ao banco", Toast.LENGTH_SHORT).show();
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT Id, Nome, SenhaHash FROM Usuarios WHERE Email = ?")) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int usuarioId = rs.getInt("Id");
                    String nome = rs.getString("Nome");
                    String senhaHashArmazenada = rs.getString("SenhaHash");

                    String senhaHashUsuario = hashPassword(senhaUsuario);

                    if (senhaHashArmazenada.equals(senhaHashUsuario)) {
                        Toast.makeText(getApplicationContext(), "Bem vindo!", Toast.LENGTH_SHORT).show();

                        // Armazena o nome de usuário e o ID nas preferências compartilhadas
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", nome);
                        editor.putInt("userId", usuarioId);  // Salva o userId
                        editor.apply();

                        SessaoUsuario.getInstance().setUserData(usuarioId, nome, email);

                        // Inicia a próxima atividade e passa os dados do usuário
                        Intent intent = new Intent(MainActivity.this, SaveBiteEscolha.class);
                        intent.putExtra("nome_usuario", nome);
                        intent.putExtra("usuario_id", usuarioId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Senha incorreta.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (SQLException ex) {
            Log.e("LoginError", "Erro no acesso ao banco de dados", ex);
            Toast.makeText(getApplicationContext(), "Erro no acesso ao banco de dados", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("LoginError", "Erro inesperado", e);
            Toast.makeText(getApplicationContext(), "Erro inesperado", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                Log.e("LoginError", "Erro ao fechar a conexão", e);
            }
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}