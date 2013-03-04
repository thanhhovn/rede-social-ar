package br.com.realidadeAumentada.cadastroUsuario;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import android.annotation.SuppressLint;

public class DadosPerfil {

	private String email;
	private String nome;
	private Character sexo;
	private String status_relacionamento;
	private String nivel_escolar;
	private String profissao;
	private String telefone;
	private String dt_nascimento;
	
	private  Endereco endereco = new Endereco();
	
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
	@SuppressLint("SimpleDateFormat")
	public  String getDt_nascimento() {
		Date data = null;
		try {
			data = new SimpleDateFormat("yyyy-MM-dd").parse(dt_nascimento);
		} catch (ParseException e) {
			e.printStackTrace();
		}  

       	return new SimpleDateFormat("dd-MMM-yyyy").format(data);
	}
	public  void setDt_nascimento(String dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}
	
	public  Endereco getEndereco() {
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
