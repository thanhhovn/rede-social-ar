package br.com.realidadeAumentada.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AcessoPostGres {

	   private static Connection con;
	   private static String driver = "org.postgresql.Driver";  
	   private static String user   = "postgres";  
	   private static String senha = "19433779";  
	   private static String url      = "jdbc:postgresql://localhost:5432/AR_RedeSocial";

	   public static Connection getConexao(){
	 
	       try  
	       {  
	           Class.forName(driver);  
	           con = (Connection) DriverManager.getConnection(url, user, senha);  
	 
	           System.out.println("Conexão realizada com sucesso.");  
	       }  
	       catch (ClassNotFoundException ex)  
	       {  
	           System.err.print(ex.getMessage());  
	           return null;
	       }   
	       catch (SQLException e)  
	       {  
	           System.err.print(e.getMessage());
	           return null;
	       }
	       return con;
	   }  



	   public static void desconectar(){

		 try{
		       con.close();
		       System.out.println("Conexão Finalizada.");  
		     }catch(SQLException ex){
		     	  ex.printStackTrace();
		     }
	   }
	   
	   public static void main(String[] args){
		   getConexao();
		   desconectar();
	   }

}
