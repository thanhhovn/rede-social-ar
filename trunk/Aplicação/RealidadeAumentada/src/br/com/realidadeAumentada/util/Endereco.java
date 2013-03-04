package br.com.realidadeAumentada.util;

import java.util.ArrayList;
import java.util.List;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

public class Endereco {
	private static Object[] listaEstado = null;
	private static Object[] listaCidade = null;
	private static List<String> estados = null;
	private static List<String> cidades = null;
	private static long old_idEstado;
	private static boolean flag = true;
	
	public static List<String> getEstados(){
		if(estados == null){
			try{
				MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
				chamaWBS.setMetodo(MetodosWBS.ESTADOS);					
				estados = new ArrayList<String>();
				Object  spo = (Object) chamaWBS.iniciarWBS();
				if(spo!=null){
					String retorno = spo.toString();
					String[] listaEstados = retorno.toString().split(",");
					estados.add(".:Selecione:.");
					for (String estadoTemp : listaEstados) {
						estados.add(estadoTemp);
					}
				}
			 }catch(Exception e){
				 estados = null;
			 }
			 if(flag){
				listaEstado =  estados.toArray();
				flag = false;
			 }
		}
		return estados;
	}
	
	public static List<String> getCidades(Long idEstado){
		if(!indiceIgualIndiceOld(idEstado)){
			try{
				old_idEstado = idEstado;
				MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
				chamaWBS.setMetodo(MetodosWBS.CIDADES);					
				chamaWBS.addParametro(String.valueOf(idEstado.intValue()));
				Object  spo = (Object) chamaWBS.iniciarWBS();
				cidades = new ArrayList<String>();
				if(spo!=null){
					String retorno = spo.toString();
					String[] listaCidades = retorno.toString().split(",");
					cidades.add(".:Selecione:.");
					for (String cidadeTemp : listaCidades) {
						cidades.add(cidadeTemp);
					}
				}
			 }catch(Exception e){ }
			listaCidade =  cidades.toArray();
		}
		return cidades;
	}
	
	public static boolean indiceIgualIndiceOld(Long indice){
		return (old_idEstado == indice);
	}
	
	public static int estadoIndice(String estado){
		int posicao = 0; 
		if(estado != null && estado.length() > 0){
			for (int i = 0; i < listaEstado.length; i++) {
				if(estado.equals(listaEstado[i])){
					posicao = i;
					break;
				}
			} 
		}
		return posicao;
	}
	
	public static int cidadeIndice(String cidade){
		int posicao = 0; 
		if(cidade != null && cidade.length() > 0){
			for (int i = 0; i < listaCidade.length; i++) {
				if(cidade.equals(listaCidade[i])){
					posicao = i;
					break;
				}
			} 
		}
		return posicao;
	}
}
