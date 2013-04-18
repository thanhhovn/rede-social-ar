package br.com.realidadeAumentada.cadastroUsuario;


public class DadosPerfil {

	private String email;
	private String nome;
	private Character sexo;
	private String status_relacionamento;
	private String nivel_escolar;
	private String profissao;
	private String telefone;
	private String dt_nascimento;
	
	private  DadosEndereco endereco = new DadosEndereco();
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public  String getNome() {
		return nome;
	}
	public  void setNome(String nome) {
		this.nome = nome;
	}
	public  char getSexo() {
		return sexo;
	}
	public  void setSexo(String sexo) {
		this.sexo = sexo.charAt(0);
	}
	public  String getStatus_relacionamento() {
		return status_relacionamento;
	}
	public  void setStatus_relacionamento(String status_relacionamento) {
		this.status_relacionamento = status_relacionamento;
	}
	public  String getNivel_escolar() {
		return nivel_escolar;
	}
	public  void setNivel_escolar(String nivel_escolar) {
		this.nivel_escolar = nivel_escolar;
	}
	public  String getProfissao() {
		return profissao;
	}
	public  void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public  String getTelefone() {
		return telefone;
	}
	public  void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public  String getDt_nascimento() {
		String[] data = this.dt_nascimento.split("-");
		String retorno = data[2]+"-"+data[1]+"-"+data[0];
		return retorno;
	}
	public  void setDt_nascimento(String dt_nascimento) {
		String[] data = dt_nascimento.split("-");
		String dataFormatada = data[2]+"-"+data[1]+"-"+data[0];
		this.dt_nascimento = dataFormatada;
	}
	
	public  DadosEndereco getEndereco() {
		return endereco;
	}

	public  void clear(){
		email = null;
		nome = null;
		sexo = null;
		status_relacionamento = null;
		nivel_escolar = null;
		profissao = null;
		telefone = null;
		dt_nascimento = null;
	}
}
