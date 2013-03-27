package br.com.realidadeAumentada.cadastroUsuario;

import java.util.ArrayList;
import java.util.List;

import br.com.realidadeAumentada.maps.Marcador;

public class Usuario {
	public  static DadosCadastro dadosLogin = new DadosCadastro();
	public  static DadosPerfil	dadosPerfil = new DadosPerfil();
	private static String usuario_id;
	// Essa lista irá conter as marcações pertencentes ao usuário que esta logado no sistema.
	private static List<Marcador> listaMarcadores = new ArrayList<Marcador>(0);
	
	public static void newInstance(){
		dadosLogin  = new  DadosCadastro();
		dadosPerfil = new DadosPerfil();
		listaMarcadores = new ArrayList<Marcador>(0);
		setUsuario_id(null);
	}

	public static String getUsuario_id() {
		return usuario_id;
	}

	public static void setUsuario_id(String usuario_id) {
		Usuario.usuario_id = usuario_id;
	}
	
	public static void addMarcador(Marcador marcador){
		if(marcador != null && !listaMarcadores.contains(marcador)){
			listaMarcadores.add(marcador);
		}
	}
	
	public static void removeMarcador(Marcador marcador){
		if(marcador != null && listaMarcadores.contains(marcador)){
			listaMarcadores.remove(marcador);
		}
	}
	
	public static boolean contemMarcador(Marcador marcador){
		if(marcador != null && listaMarcadores.contains(marcador)){
			return true;
		}else{
			return false;
		}
	}
}
