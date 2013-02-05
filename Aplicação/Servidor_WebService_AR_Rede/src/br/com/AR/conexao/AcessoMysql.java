package br.com.AR.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Lucas F
 */
public class AcessoMysql {
   private static Connection con;

	  public static Connection getConexao(){
	    try{
	           Class.forName("com.mysql.jdbc.Driver");
		   con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/locadora?user=root&password=19433779");
	    }catch(ClassNotFoundException ex){
	    	   ex.printStackTrace();
	    }catch(SQLException ex){
	    	   ex.printStackTrace();
	    }
	    return  con;
	}

	public static void desconectar(){
	    try{
	          con.close();
	    }catch(SQLException ex){
	    	  ex.printStackTrace();
	    }
	}
}
