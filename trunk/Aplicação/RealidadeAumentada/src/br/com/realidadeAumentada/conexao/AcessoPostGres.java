package br.com.realidadeAumentada.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AcessoPostGres {

   private static Connection con;
   private static String driver = "org.postgresql.Driver";  
   private static String user   = "postgres";  
   private static String senha = "19433779";  
   private static String url      = "jdbc:postgresql://localhost:5432/ra_redeSocial";

   public static Connection getConexao(){
 
       try  
       {  
           Class.forName(driver);  
           Connection con = null;  
 
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
	     }catch(SQLException ex){
	     	  ex.printStackTrace();
	     }
   }

}
