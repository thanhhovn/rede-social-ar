package br.com.realidadeAumentada.cadastroUsuario;

public class Usuario {
	public  static DadosCadastro dadosLogin = new DadosCadastro();
	public  static DadosPerfil	dadosPerfil = new DadosPerfil();
	private static String usuario_id;
	
	public static void newInstance(){
		dadosLogin  = new  DadosCadastro();
		dadosPerfil = new DadosPerfil();
		setUsuario_id(null);
	}

	public static String getUsuario_id() {
		return usuario_id;
	}

	public static void setUsuario_id(String usuario_id) {
		Usuario.usuario_id = usuario_id;
	}
}
