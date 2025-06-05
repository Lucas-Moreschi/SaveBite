package br.com.savebite.service;

import br.com.savebite.dto.UsuarioDto;
import br.com.savebite.entity.Usuario;

public interface LoginService {
	
	public Usuario autenticar(UsuarioDto usuario);

}
