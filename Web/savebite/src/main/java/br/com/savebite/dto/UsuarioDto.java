package br.com.savebite.dto;

public class UsuarioDto {
	
	private String nome;
	private String email;
	private String senhaHash;
	private String tipoUsuario;

	public UsuarioDto(String email, String senhaHash) {
		super();
		this.email = email;
		this.senhaHash = senhaHash;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenhaHash() {
		return senhaHash;
	}
	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	@Override
	public String toString() {
		return "UsuarioDto [nome=" + nome + ", email=" + email + ", senhaHash=" + senhaHash + ", tipoUsuario="
				+ tipoUsuario + "]";
	}
	
}
