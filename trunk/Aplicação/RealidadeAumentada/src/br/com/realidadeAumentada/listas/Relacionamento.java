package br.com.realidadeAumentada.listas;

public class Relacionamento{ 
	
	private String name;
	private boolean marcado;

	public Relacionamento() {
	}
	
	public Relacionamento(String name) {
		this.setName(name);
	}

	public Relacionamento(String name, boolean marcado) {
		this.setName(name);
		this.setMarcado(marcado);
	}
	
	public static String tipoRelacionamentos(char tipo){
		
		switch (tipo) {
		case 'S': return "Solteiro (a)"; 
		case 'N': return "Namorando (a)"; 	
		case 'C': return "Casado (a)";
		case 'E': return "Enrolado";
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}

	public boolean isMarcado() {
		return marcado;
	}

}
