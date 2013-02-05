package br.com.AR.webService;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.AR.conexao.AcessoMysql;

public class Locadora{

	 private static PreparedStatement pstm;
	 private static ResultSet rs;
	 
	 private List<FilmeBean> filmes;

	public Locadora(){		
	}
	
	public boolean verificarDisponibilidade(String filme) {
		String busca = null;
		String consulta = 
						"SELECT * FROM filme WHERE titulo = ? AND disponivel = 1";
		boolean disponivel = false;
		try{
	    	   busca = consulta;
	           pstm = AcessoMysql.getConexao().prepareStatement(busca);
	           pstm.setString(1,filme);
	           rs = pstm.executeQuery(); // rs contem o resultado da consulta
	           
	           if(rs.next()){
            	   disponivel = true;
	           }
		}catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	           AcessoMysql.desconectar();
	       }
		return disponivel;
	}

	public void locarFilme(String filme) {
		
	       String alterarDisponibilidade =
							    	      "UPDATE filme SET disponivel = 0"
							    	     +" WHERE titulo = ?";
	       try{
	          pstm = AcessoMysql.getConexao().prepareStatement(alterarDisponibilidade);
	          pstm.setString(1,filme);
	          pstm.executeUpdate();
	       }catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	           AcessoMysql.desconectar();
	       }
	}

	
	public void devolverFilme(String filme) {
	       String alterarDisponibilidade =
	    	      "UPDATE filme SET disponivel =1"
	    	     +" WHERE titulo = ?";

			try{
				pstm = AcessoMysql.getConexao().prepareStatement(alterarDisponibilidade);
				pstm.setString(1, filme);
				pstm.executeUpdate();
			}catch(Exception erro){
				erro.printStackTrace();
			}
			finally{
				AcessoMysql.desconectar();
			}
	}

	
	public FilmeBean consultarFilme(String filme) {				
		
		String busca = null;
		String consulta = 
						"SELECT * FROM filme WHERE titulo = ?";
		FilmeBean filmeBean = null;
		try{
	    	   busca = consulta;
	           pstm = AcessoMysql.getConexao().prepareStatement(busca);
	           pstm.setString(1,filme);
	           rs = pstm.executeQuery(); // rs contem o resultado da consulta
	           
	           if(rs.next()){
	        	   filmeBean = new FilmeBean();
	        	   filmeBean.setCod_filme(rs.getInt("cod_filme"));
	        	   filmeBean.setTitulo(rs.getString("titulo"));
	        	   filmeBean.setCategoria(rs.getString("categoria"));
	        	   filmeBean.setValor_locacao(rs.getDouble("valor_locacao"));
	        	   filmeBean.setDisponivel(rs.getInt("disponivel"));
	           }
		}catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	           AcessoMysql.desconectar();
	       }
		return filmeBean;
	}

	
	public List<FilmeBean> listarFilmes() {
	   
	   if(this.filmes == null){
		   filmes =  new ArrayList<FilmeBean>();
		   filmes();
	   }	   
       return this.filmes;
	}
	
	private void filmes (){
		   String busca = null;
//		   List<FilmeBean> filmes = new ArrayList<FilmeBean>();

	       String consultaTotos =
	    	   					"SELECT * FROM filme";
	       try{
	    	   busca = consultaTotos;
	           pstm = AcessoMysql.getConexao().prepareStatement(busca);
	           rs = pstm.executeQuery(); // rs contem o resultado da consulta
	           
	           FilmeBean filmeBean;
	           while(rs.next()){
	        	   filmeBean = new FilmeBean();
	        	   filmeBean.setCod_filme(rs.getInt("cod_filme"));
	               
	        	   filmeBean.setTitulo(rs.getString("titulo"));
	        	   filmeBean.setCategoria(rs.getString("categoria"));
	        	   filmeBean.setValor_locacao(rs.getDouble("valor_locacao"));
	        	   filmeBean.setDisponivel(rs.getInt("disponivel"));

	               this.filmes.add(filmeBean);
	           }
	       }catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	           AcessoMysql.desconectar();
	       }
	}
	


}
