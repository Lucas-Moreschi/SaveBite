package br.com.savebite.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.savebite.entity.IngredienteReceita;
import br.com.savebite.entity.Receita;

@Repository
public class ReceitaRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Buscar receitas por usuário
    @SuppressWarnings("deprecation")
	public List<Receita> buscarReceitasPorUsuario(Long usuarioId) {
        try {
            String sql = "SELECT * FROM Receitas WHERE UsuarioId = ?";
            return jdbcTemplate.query(sql, new Object[]{usuarioId}, (rs, rowNum) -> 
                new Receita(
                    rs.getLong("Id"),
                    rs.getString("Nome"),
                    rs.getString("Descricao"),
                    rs.getInt("TempoPreparo"),
                    rs.getInt("Porcoes"),
                    rs.getLong("CategoriaId"),
                    rs.getString("Dificuldade"),
                    rs.getLong("UsuarioId"),
                    rs.getDouble("MediaAvaliacao"),
                    rs.getDate("DataCriacao")
                )
            );
        } catch (EmptyResultDataAccessException e) {
            // Caso não encontre receitas, podemos retornar uma lista vazia
            return List.of();
        }
    }

    // Buscar receita por ID
    public Receita buscarPorId(Long id) {
        String sql = "SELECT * FROM Receitas WHERE Id = ?";
        try {
            Receita receita = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Receita.class), id);
            // Adicionar ingredientes à receita
            receita.setIngredientes(buscarIngredientesPorReceitaId(id));
            return receita;
        } catch (EmptyResultDataAccessException e) {
            return null; // Ou outra lógica caso não encontre
        }
    }

    // Atualizar receita
    public void atualizarReceita(Receita receita) {
        String sql = "UPDATE Receitas SET Nome = ?, Descricao = ?, TempoPreparo = ?, Porcoes = ?, Dificuldade = ? WHERE Id = ?";
        jdbcTemplate.update(sql, receita.getNome(), receita.getDescricao(), receita.getTempoPreparo(),
                receita.getPorcoes(), receita.getDificuldade(), receita.getId());
    }

    // Salvar receita (insert ou update)
    public Receita salvar(Receita receita) {
        if (receita.getId() == null) {
            // Inserir nova receita
            String sql = "INSERT INTO Receitas (Nome, Descricao, TempoPreparo, Porcoes, Dificuldade, UsuarioId) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, receita.getNome(), receita.getDescricao(), receita.getTempoPreparo(),
                    receita.getPorcoes(), receita.getDificuldade(), receita.getUsuarioId());

            // Retornar a receita com o ID gerado
            String idSql = "SELECT SCOPE_IDENTITY()";
            Long id = jdbcTemplate.queryForObject(idSql, Long.class);
            receita.setId(id);
        } else {
            // Atualizar receita existente
            String sql = "UPDATE Receitas SET Nome = ?, Descricao = ?, TempoPreparo = ?, Porcoes = ?, Dificuldade = ? WHERE Id = ?";
            jdbcTemplate.update(sql, receita.getNome(), receita.getDescricao(), receita.getTempoPreparo(),
                    receita.getPorcoes(), receita.getDificuldade(), receita.getId());
        }
        return receita;
    }

    // Buscar ingredientes associados à receita
    @SuppressWarnings("deprecation")
	private List<IngredienteReceita> buscarIngredientesPorReceitaId(Long receitaId) {
        String sql = "SELECT i.Nome AS NomeIngrediente, ir.Quantidade, m.Unidade AS Medida " +
                     "FROM Ingredientes i " +
                     "JOIN Ingredientes_Receitas ir ON i.Id = ir.IngredienteId " +
                     "JOIN Medidas m ON ir.MedidaId = m.Id " +
                     "WHERE ir.ReceitaId = ?";
        
        return jdbcTemplate.query(sql, new Object[]{receitaId}, new RowMapper<IngredienteReceita>() {
            @Override
            public IngredienteReceita mapRow(ResultSet rs, int rowNum) throws SQLException {
                IngredienteReceita ingrediente = new IngredienteReceita();
                ingrediente.setNomeIngrediente(rs.getString("NomeIngrediente"));
                ingrediente.setQuantidade(rs.getDouble("Quantidade"));
                ingrediente.setMedida(rs.getString("Medida"));
                return ingrediente;
            }
        });
    }
    
    public void deletarReceita(Long receitaId) {
        try {
            String deleteInstrucoes = "DELETE FROM Instrucoes WHERE ReceitaId = ?";
            jdbcTemplate.update(deleteInstrucoes, receitaId);

            String deleteIngredientesReceitas = "DELETE FROM Ingredientes_Receitas WHERE ReceitaId = ?";
            jdbcTemplate.update(deleteIngredientesReceitas, receitaId);

            String deleteAvaliacoes = "DELETE FROM Avaliacoes WHERE ReceitaId = ?";
            jdbcTemplate.update(deleteAvaliacoes, receitaId);

            String deleteReceita = "DELETE FROM Receitas WHERE Id = ?";
            jdbcTemplate.update(deleteReceita, receitaId);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a receita com ID: " + receitaId, e);
        }
    }
}
