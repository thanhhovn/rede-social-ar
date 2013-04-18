package br.com.realidadeAumentada.cadastroUsuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.maps.OverlayItem;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import br.com.realidadeAumentada.R;
import br.com.realidadeAumentada.maps.Marcador;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

public class Usuario {
	public static  DadosCadastro dadosLogin = new DadosCadastro();
	public static  DadosPerfil	dadosPerfil = new DadosPerfil();
	private static String usuario_id;
	private static String usuarioLogado_id;
	private static List<Usuario> listaUsuario = new ArrayList<Usuario>(0);
	// Essa lista irá conter as marcações pertencentes ao usuário que esta logado no sistema.
	private static List<Marcador> listaMarcadores = new ArrayList<Marcador>(0);
	
	private static Map<Integer,Amigo> listaAmigos = new HashMap<Integer,Amigo>();
	private static Map<Integer,Usuario> listaUsuarios = new HashMap<Integer,Usuario>();
	
	public static void newInstance(){
		dadosLogin  = new  DadosCadastro();
		dadosPerfil = new DadosPerfil();
		listaMarcadores = new ArrayList<Marcador>(0);
		listaAmigos = new HashMap<Integer,Amigo>();
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

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void addListaUsuario(Usuario usuario) {
		if(!listaUsuario.contains(usuario)){
			listaUsuario.add(usuario);
		}
	}
	
	public static String amigosPelaPosicao(Integer posicao) {
		Usuario usuario = listaAmigos.get(posicao);
		return usuario.getUsuario_id();
	}
	
	public static String usuarioPelaPosicao(Integer posicao) {
		Usuario usuario = listaUsuarios.get(posicao);
		return usuario.getUsuario_id();
	}

	public static void addAmigo(int id, Amigo amigo){
		listaAmigos.put(id, amigo);
	}
	
	public static void addUsuario(int id, Amigo usuario){
		listaUsuarios.put(id, usuario);
	}
	
	public static List<String> getAmigos(String idUsuario){
		List<String> amigos = null;
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.LISTA_AMIGOS);					
			chamaWBS.addParametro(idUsuario);
			Object  spo = (Object) chamaWBS.iniciarWBS();
			amigos = new ArrayList<String>();
			if(!spo.equals("ERRO")){
				String retorno = spo.toString();
				String[] dadosAmigo = retorno.toString().split(",");
				Amigo amigo = new Amigo();
				
				amigos.add(".:Selecione:.");
				boolean impar = true;
				boolean flag = false;
				Integer posicao = 1;
				for (Integer i = 0; i < dadosAmigo.length; i++) {
					if(impar){
						amigo.setUsuario_id(dadosAmigo[i]);
						impar = false;
					}else{
						amigo.setNome(dadosAmigo[i]);
						impar = true;
						flag = true;
					}
					if(flag){
						addAmigo(posicao++, amigo);
						amigos.add(amigo.getNome());
						amigo = new Amigo();
						flag = false;
					}
				}
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return amigos;
	}
	
	public static List<String> getUsuarios(String idUsuario){
		List<String> usuarios = null;
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.LISTA_USUARIOS);					
			chamaWBS.addParametro(idUsuario);
			Object  spo = (Object) chamaWBS.iniciarWBS();
			usuarios = new ArrayList<String>();
			if(spo!=null){
				String retorno = spo.toString();
				String[] dadosAmigo = retorno.toString().split(",");
				Amigo amigo = new Amigo();
				
				usuarios.add(".:Selecione:.");
				boolean impar = true;
				boolean flag = false;
				Integer posicao = 1;
				for (Integer i = 0; i < dadosAmigo.length; i++) {
					if(impar){
						amigo.setUsuario_id(dadosAmigo[i]);
						impar = false;
					}else{
						amigo.setNome(dadosAmigo[i]);
						impar = true;
						flag = true;
					}
					if(flag){
						addUsuario(posicao++, amigo);
						usuarios.add(amigo.getNome());
						amigo = new Amigo();
						flag = false;
					}
				}
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return usuarios;
	}
	
	public static String getUsuarioLogado_id() {
		return usuarioLogado_id;
	}

	public static void setUsuarioLogado_id(String usuarioLogado_id) {
		Usuario.usuarioLogado_id = usuarioLogado_id;
	}

	public static boolean enviarMensagem(Integer posicao,String mensagem){
		String idUsuarioRecepitor = amigosPelaPosicao(posicao);
		String idUsuarioEmissor = getUsuarioLogado_id();
		try{
			GregorianCalendar calendar = new GregorianCalendar();
			Date date = calendar.getTime();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			String data = formato.format(date); 
			StringBuilder dados = new StringBuilder();
			dados.append(idUsuarioEmissor+",");
			dados.append(idUsuarioRecepitor+",");
			dados.append(mensagem+",");
			dados.append(data);
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.ENVIAR_MENSAGEM);					
			chamaWBS.addParametro(dados.toString());
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo!=null){
				return true;
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return false;
	}
	
	public static boolean addContato(String idUsuario, Integer posicao){
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.ADICIONAR_CONTATO);		
			String idAmigo = usuarioPelaPosicao(posicao);
			chamaWBS.addParametro(idUsuario);
			chamaWBS.addParametro(idAmigo);
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo!=null){
				return true;
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return false;
	}

	public static String usuarioLogado(){
		return "Usuário: "+dadosPerfil.getNome();
	}
	
	
}
