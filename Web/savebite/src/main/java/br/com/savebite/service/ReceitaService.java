package br.com.savebite.service;

import java.util.List;

import br.com.savebite.entity.Receita;

public interface ReceitaService {
	
	public List<Receita> listarReceitas(Long id);
	
	public void atualizarReceita(Receita receita);
	
	public Receita buscarPorId(Long id);
	
	public void deletarReceita(Long id);
	
}
