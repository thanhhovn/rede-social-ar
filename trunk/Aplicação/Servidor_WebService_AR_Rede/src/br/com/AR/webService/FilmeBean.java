package br.com.AR.webService;


public class FilmeBean {

	private Integer cod_filme;
	private String titulo;
	private String categoria;
	private Double valor_locacao;
	private Integer disponivel;
	
	public FilmeBean(){}

	public Integer getCod_filme() {
		return cod_filme;
	}

	public void setCod_filme(Integer cod_filme) {
		this.cod_filme = cod_filme;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getValor_locacao() {
		return valor_locacao;
	}

	public void setValor_locacao(Double valor_locacao) {
		this.valor_locacao = valor_locacao;
	}

	public Integer getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Integer disponivel) {
		this.disponivel = disponivel;
	}

}
