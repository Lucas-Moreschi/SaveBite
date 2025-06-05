package br.com.savebite.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.savebite.entity.Usuario;

@Repository
public class UsuarioRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	public Usuario autenticar(String email, String senhaHash) {
	    try {
	        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senhaHash = ?";
	        return jdbcTemplate.queryForObject(sql, new Object[]{email, senhaHash}, (rs, rowNum) -> 
	            new Usuario(
	                rs.getLong("id"),
	                rs.getString("nome"),
	                rs.getString("email"),
	                rs.getString("senhaHash"),
	                rs.getString("tipoUsuario")
	            )
	        );
	    } catch (EmptyResultDataAccessException e) {
	        // Caso o usuário não seja encontrado, retornamos null ou uma exceção
	        return null;
	    }
	}
}
