package br.com.AR.webService;

import java.util.ArrayList;
import java.util.List;

public class TesteConexao {
	
	public static void main(String[] args){
		AR_RedeWS ws = new AR_RedeWS();
		

		//System.out.println(retorno);
		//FilmeBean filme = ws.consultarFilme("Até que termine o dia");
		//System.out.println(filme.getCategoria());
		
	/*	String enderecoId = "1";
		String email = "@";
		String sexo = "M";
		String nome = "lucas";
		String relacionamento = "S";
		String escolaridade = "estudando";
		String profissao = "medico";
		String telefone = "99818201";
		StringBuilder usuarios = new StringBuilder();
		usuarios.append("enderecoId : "+enderecoId+",");
		usuarios.append("email:"+email+",");
		usuarios.append("nome:"+nome+",");
		usuarios.append("sexo:"+sexo+",");
		usuarios.append("status_relacionamento:"+relacionamento+",");
		usuarios.append("nivel_escolar:"+escolaridade+",");
		usuarios.append("profissao:"+profissao+",");
		usuarios.append("telefone:"+telefone+",");
	*/	


//================================Executando WEB Service================================================
		//String retorno =  ws.RegistrarMarcacaoGPS("4","2.2", "2.4", "Descricao");
	/*	String retorno = ws.PercursoUsuario("4");
		String[] listaPercursos = retorno.toString().split(",");
		StringBuilder percursos = new StringBuilder();
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < listaPercursos.length; i++) {
			if(!listaPercursos[i].equals(":")){
				percursos.append(listaPercursos[i]+",");
			}else{
				lista.add(percursos.toString());
				percursos = new StringBuilder();
			}
		}
	*/	
		
		String retorno = ws.exibeTodosPontos();
		String[] listaPercursos = retorno.toString().split(",");
		StringBuilder percursos = new StringBuilder();
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < listaPercursos.length; i++) {
			if(!listaPercursos[i].equals(":")){
				percursos.append(listaPercursos[i]+",");
			}else{
				lista.add(percursos.toString());
				percursos = new StringBuilder();
			}
		}
//=========================== Fim da implementação do código para o cliente (Aplicação Android) ========		
		
		for (String string : lista) {
			System.out.println(string);
		}
		
	/*	String[] listaPercusosUsuario = percursos.toString().split(":");
		//String estados = ws.getEstados();
		//String[] campoValor = estados.toString().split(",");
		
		for (int i = 0; i < listaPercusosUsuario.length; i++) {
			String campo_valor = listaPercusosUsuario[i];
			
			lista.add(campo_valor);
		}
		for (String string : lista) {
			System.out.println(string);
		}
		*/
		
	}
}
