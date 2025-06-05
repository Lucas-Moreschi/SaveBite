package com.example.prjsavebite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);  // Define o layout criado acima

        // Temporizador para exibir a splash screen por um breve período
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia a MainActivity após o delay
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Fecha a SplashActivity para não voltar ao histórico
            }
        }, 10000);  // Tempo de exibição em milissegundos
    }
}