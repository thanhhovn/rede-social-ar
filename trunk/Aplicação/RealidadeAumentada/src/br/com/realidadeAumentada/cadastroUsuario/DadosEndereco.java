package br.com.realidadeAumentada.cadastroUsuario;

public class DadosEndereco {
	
	private  String enderecoId;
	private  String nome_pais;
	private  String nome_Estado;
	private  String sigla_estado;
	private  String cod_cidade;
	private  String nome_cidade;
	private  String nome_bairro;
	private  String nome_logradouro;
	private  String numero_endereco;
	
	public  String getCod_cidade() {
		return cod_cidade;
	}
	public  void setCod_cidade(String cod_cidade) {
		this.cod_cidade = cod_cidade;
	}
	public  String getNome_bairro() {
		return nome_bairro;
	}
	public  void setNome_bairro(String nome_bairro) {
		this.nome_bairro = nome_bairro;
	}
	public  String getNome_logradouro() {
		return nome_logradouro;
	}
	public  void setNome_logradouro(String nome_logradouro) {
		this.nome_logradouro = nome_logradouro;
	}
	public  String getNumero_endereco() {
		return numero_endereco;
	}
	public  void setNumero_endereco(String numero_endereco) {
		this.numero_endereco = numero_endereco;
	}
	
	public  String getNome_pais() {
		return nome_pais;
	}
	public  void setNome_pais(String nome_pais) {
		this.nome_pais = nome_pais;
	}
	public  String getSigla_estado() {
		return sigla_estado;
	}
	public  void setSigla_estado(String sigla_estado) {
		this.sigla_estado = sigla_estado;
	}
	public  String getNome_cidade() {
		return nome_cidade;
	}
	public  void setNome_cidade(String nome_cidade) {
		this.nome_cidade = nome_cidade;
	}
	
	public  void clear(){
		cod_cidade = null;
		nome_bairro = null;
		nome_logradouro = null;
		numero_endereco = null;
		nome_pais = null;
		sigla_estado = null;
		nome_cidade = null;
	}
	public String getEnderecoId() {
		return enderecoId;
	}
	public void setEnderecoId(String enderecoId) {
		this.enderecoId = enderecoId;
	}
	public String getNome_Estado() {
		return nome_Estado;
	}
	public void setNome_Estado(String nome_Estado) {
		this.nome_Estado = nome_Estado;
	}
}
