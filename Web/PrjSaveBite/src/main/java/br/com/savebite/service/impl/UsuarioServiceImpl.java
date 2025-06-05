package br.com.savebite.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import br.com.savebite.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Override
	public String senhaHash(String password) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar o hash da senha", e);
        }
    }
	
	

}
