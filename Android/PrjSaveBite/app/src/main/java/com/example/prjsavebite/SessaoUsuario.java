package com.example.prjsavebite;

public class SessaoUsuario {
    private static SessaoUsuario instance;
    private int userId;
    private String username;
    private String email;

    // Construtor privado para impedir a instância externa
    private SessaoUsuario() {}

    // Método para obter a instância única
    public static synchronized SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    // Métodos para configurar os dados do usuário
    public void setUserData(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    // Limpar os dados do usuário (para logout)
    public void clearUserData() {
        this.userId = -1;
        this.username = null;
        this.email = null;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // Método para limpar os dados da sessão
    public void clearSession() {
        userId = -1;
        username = null;
        email = null;
    }
}
