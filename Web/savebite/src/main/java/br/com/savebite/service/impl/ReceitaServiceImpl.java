package br.com.savebite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.savebite.entity.Receita;
import br.com.savebite.repository.ReceitaRepository;
import br.com.savebite.service.ReceitaService;

@Service
public class ReceitaServiceImpl implements ReceitaService{

	@Autowired
	ReceitaRepository receitaRepository;
	
	@Override
	public List<Receita> listarReceitas(Long id) {
		return receitaRepository.buscarReceitasPorUsuario(id);
	}

	@Override
	public void atualizarReceita(Receita receita) {
		receitaRepository.salvar(receita);
		
	}

	@Override
	public Receita buscarPorId(Long id) {
		return receitaRepository.buscarPorId(id);
	}

	@Override
	public void deletarReceita(Long id) {
		receitaRepository.deletarReceita(id);
	}

	
}
