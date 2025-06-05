package br.com.savebite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.savebite.dto.UsuarioDto;
import br.com.savebite.entity.Receita;
import br.com.savebite.entity.Usuario;
import br.com.savebite.service.LoginService;
import br.com.savebite.service.ReceitaService;
import br.com.savebite.service.UsuarioService;

@Controller
public class UserController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ReceitaService receitaService;
	
	@PostMapping("/login")
	public String autenticarUsuario(@RequestParam("email") String email, 
            						@RequestParam("senha") String senha, Model model) {
		UsuarioDto usuarioDto = new UsuarioDto(email, usuarioService.senhaHash(senha));
		Usuario usuario = loginService.autenticar(usuarioDto);
		System.out.println(usuarioDto.toString());
		if (usuario != null) {
		model.addAttribute("autenticado", true);
		model.addAttribute("email", usuario.getEmail());
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("id", usuario.getId());
		return mostrarPainelUsuario(model);
		}
		model.addAttribute("autenticado", false);
		return "redirect:/cadastro-login";
		
	}
	
	@PostMapping("/cadastro")
	public String cadastrarUsuario(@RequestParam("email") String email, 
            						@RequestParam("senha") String senha,
            						@RequestParam("email") String nome, 
            						@RequestParam("senha") String confirmarSenha,
            						Model model) {
									
		UsuarioDto usuarioDto = new UsuarioDto(email, usuarioService.senhaHash(senha));
		Usuario usuario = loginService.autenticar(usuarioDto);
		System.out.println(usuarioDto.toString());
		if (usuario != null) {
		model.addAttribute("autenticado", true);
		model.addAttribute("email", usuario.getEmail());
		model.addAttribute("nome", usuario.getNome());
		model.addAttribute("id", usuario.getId());
		return mostrarPainelUsuario(model);
		}
		model.addAttribute("autenticado", false);
		return "redirect:/cadastro-login";
		
	}
	
	
	@GetMapping("/painel-usuario")
	public String mostrarPainelUsuario(Model model) {
		List<Receita> lista = receitaService.listarReceitas(8L);
		
		model.addAttribute("lista", lista);
		
		return "painel-usuario";
	}
	
	@GetMapping("/editar/{id}")
	public String editarReceita(@PathVariable Long id, Model model) {
	    Receita receita = receitaService.buscarPorId(id);
	    model.addAttribute("receita", receita);
	    return "editar-receita";
	}
	
    @PostMapping("/editar")
    public String salvarEdicaoReceita(@ModelAttribute Receita receita) {
        receitaService.atualizarReceita(receita);
        return "redirect:/receitas";
    }
    
    @GetMapping("/receita/{id}")
	public String visualizarReceita(@PathVariable Long id, Model model) {
	    Receita receita = receitaService.buscarPorId(id);
	    model.addAttribute("receita", receita);
	    return "receita";
	}
    
    @GetMapping("remover-receita/{id}")
    public String deletarReceita(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {

            receitaService.deletarReceita(id);

            redirectAttributes.addFlashAttribute("message", "Receita excluída com sucesso!");

            return "redirect:/painel-usuario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao excluir a receita: " + e.getMessage());

            return "redirect:/painel-usuario";
        }
    }
}
