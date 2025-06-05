package br.com.savebite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.savebite.dto.UsuarioDto;
import br.com.savebite.entity.Usuario;
import br.com.savebite.repository.UsuarioRepository;
import br.com.savebite.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario autenticar(UsuarioDto usuario) {
		return usuarioRepository.autenticar(usuario.getEmail(), usuario.getSenhaHash());
	}

}
