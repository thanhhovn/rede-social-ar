package br.com.realidadeAumentada.cadastroUsuario;

public class DadosCadastro {
	private  String email;
	private  String senha;
	private  String nome_login;
	private  String dataLogin;
	
	public  String getEmail() {
		return email;
	}
	public  void setEmail(String email) {
		this.email = email;
	}
	public  String getSenha() {
		return senha;
	}
	public  void setSenha(String senha) {
		this.senha = senha;
	}
	public  String getNome_login() {
		return nome_login;
	}
	public  void setNome_login(String nome_login) {
		this.nome_login = nome_login;
	}
	public  String getDataLogin() {
		return dataLogin;
	}
	public  void setDataLogin(String dataLogin) {
		String dadosUsuario = dataLogin.toString().substring(0, 10);
		String[] data = dadosUsuario.split("-");
		String dia = data[2];
		String mes = data[1];
		String ano = data[0];
		this.dataLogin = dia+"/"+mes+"/"+ano;
	}
	
	public void clear(){
		email = null;
		senha = null;
		nome_login = null;
		dataLogin = null;
	}
	
	public String getUltimoAcesso(){
		return "Último acesso: "+this.dataLogin;
	}
}
