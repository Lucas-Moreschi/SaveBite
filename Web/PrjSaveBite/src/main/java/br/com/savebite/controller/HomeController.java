package br.com.savebite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String enderecoPadrao() {
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String abrirHome() {
		return "home";
	}
	
	@GetMapping("/sobre")
	public String abrirSobre() {
		return "sobre";
	}
	
	@GetMapping("/funcionalidades")
	public String abrirFuncionalidades() {
		return "funcionalidades";
	}
	
	@GetMapping("/cadastro-login")
	public String abrircadastroLogin() {
		return "cadastro-login";
	}
	
	@GetMapping("/downloads")
	public String abrirDownloads() {
		return "downloads";
	}
	
	@GetMapping("/recuperar-senha")
	public String abrirRecuperarSenha() {
		return "recuperar-senha";
	}
}
