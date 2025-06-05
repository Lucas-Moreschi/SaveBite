package com.example.prjsavebite;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Acessa {

    ResultSet RS;
    java.sql.Statement stmt;
    Connection con;


    public void testarConexaoSimples(Context ctx) {
        String url = "jdbc:jtds:sqlserver://192.168.0.203:1433/ReceitasDB";
        String user = "sa";
        String password = "Braugarten%%100%1";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null && !conn.isClosed()) {
                Toast.makeText(ctx.getApplicationContext(), "Tudo certo!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            Toast.makeText(ctx.getApplicationContext(), "Erro de conexão: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Connection entBanco(Context ctx) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            Toast.makeText(ctx.getApplicationContext(), "Driver não carregado: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

        try {
            String url = "jdbc:jtds:sqlserver://192.168.0.203:1433/ReceitasDB";
            con = DriverManager.getConnection(url, "sa", "Braugarten%%100%1");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Toast.makeText(ctx.getApplicationContext(), "Erro de conexão: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return con;
    }
}




