package br.com.AR.webService;

import br.com.AR.webService.InterfaceWS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.jws.WebService;
import br.com.AR.conexao.AcessoPostGres;

@WebService(serviceName="HelloWSServiceService", 
			portName="HelloWSService", 
			endpointInterface="defaultnamespace.HelloWSService", 
			targetNamespace="http://DefaultNamespace", 
			wsdlLocation="WEB-INF/wsdl/NewWebServiceFromWSDL/HelloWSService.wsdl")
public class AR_RedeWS implements InterfaceWS {

	private static PreparedStatement pstm;
    private static ResultSet rs;
	
//	public FilmeBean consultarFilme(String filme) {
//		String busca = null;
//		String consulta = "SELECT * FROM filme WHERE titulo = ?";
//		FilmeBean filmeBean = null;
//		PreparedStatement pstm;
//		ResultSet rs;
//		try{
//	    	   busca = consulta;
//	           pstm = AcessoMysql.getConexao().prepareStatement(busca);
//	           pstm.setString(1,filme);
//	           rs = pstm.executeQuery();
//	           
//	           if(rs.next()){
//	        	   filmeBean = new FilmeBean();
//	        	   filmeBean.setCod_filme(rs.getInt("cod_filme"));
//	        	   filmeBean.setTitulo(rs.getString("titulo"));
//	        	   filmeBean.setCategoria(rs.getString("categoria"));
//	        	   filmeBean.setValor_locacao(rs.getDouble("valor_locacao"));
//	        	   filmeBean.setDisponivel(rs.getInt("disponivel"));
//	           }
//		}catch(Exception erro){
//	           erro.printStackTrace();
//	       }
//	       finally{
//	           AcessoMysql.desconectar();
//	       }
//		FilmeBean filmeBean = new FilmeBean();
//		filmeBean.setCod_filme(1);
//		filmeBean.setCategoria("Romance");
//		filmeBean.setTitulo(filme);
//		return filmeBean;
//	}
	
	public String registrarMarcacaoGPS(String idUsuario,String x, String y, String descricao) {

		String marcacao = "INSERT INTO cliente.tb_localizacao " +
						  "			   (localizacao_usuario_id," +
						  "				pontox," +
						  "				pontoy," +
						  "				ds_localizacao)"
						  +"VALUES(?,?,?,?)";
		try{
		pstm = AcessoPostGres.getConexao().prepareStatement(marcacao);
		pstm.setInt(1,Integer.valueOf(idUsuario));
		pstm.setString(2,x);
		pstm.setString(3,y);
		pstm.setString(4,descricao);
		pstm.executeUpdate();
		}catch(Exception erro){
			erro.printStackTrace();
		}
		finally{
			AcessoPostGres.desconectar();
		}
		return "Cadastrado com Sucesso!";
	}

	public String resposta() {return "Sucesso";}

	
	public String login(String nomeLogin, String senha) {
		return null;
	}
	
	public String getEstados() {
		String consulta = "SELECT sgl_estado FROM cliente.estado";
		PreparedStatement pstm;
		ResultSet rs;
		StringBuilder estados = new StringBuilder();
		try{
           pstm = AcessoPostGres.getConexao().prepareStatement(consulta);
           rs = pstm.executeQuery();
           String estado = null;
           while(rs.next()){
        	   estado = rs.getString("sgl_estado").toString();
        	   estados.append("estado :"+estado+",");
           }
		}catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	    	   AcessoPostGres.desconectar();
	       }
		return estados.toString();
	}
	
	
	public String getMunicipios(int codEstado) {
		String consulta = "SELECT nm_cidade FROM cliente.cidade " +
						  "	where cod_estado = ? ";
		PreparedStatement pstm;
		ResultSet rs;
		StringBuilder cidades = new StringBuilder();
		try{
           pstm = AcessoPostGres.getConexao().prepareStatement(consulta);
           pstm.setInt(1,codEstado);
           rs = pstm.executeQuery();
           String cidade = null;
           while(rs.next()){
        	   cidade = rs.getString("nm_cidade").toString();
        	   cidades.append("cidade:"+cidade+",");
           }
		}catch(Exception erro){
	           erro.printStackTrace();
	       }
	       finally{
	    	   AcessoPostGres.desconectar();
	       }
		return cidades.toString();
	}

	
	public String cadastrarUsuario(String aluno) {
		return null;
	}
	
	public String percursoUsuario(String idUsuario) {
		String marcacao = "select pontox,pontoy,ds_localizacao " +
		  "	 					from cliente.tb_localizacao " +
		  "				   where localizacao_usuario_id = ?";
		StringBuilder campos = new StringBuilder();
		try{
			pstm = AcessoPostGres.getConexao().prepareStatement(marcacao);
			pstm.setInt(1,Integer.valueOf(idUsuario));
		 
			ResultSet rs = pstm.executeQuery();
		 //Mascara para a lista do percurso do Usuario
		 //pontoX,pontoY,descricao:pontoX,pontoY,descricao
			while(rs.next()){
			 campos.append(rs.getString("pontox").toString()+",");
			 campos.append(rs.getString("pontoy").toString()+",");
			 campos.append(rs.getString("ds_localizacao").toString()+",");
			 campos.append(":,");
			}
			}catch(Exception erro){
				erro.printStackTrace();
			}
			finally{
				AcessoPostGres.desconectar();
			}
			return campos.toString();
	}
	
	public String exibeTodosPontos(){
		String marcacao = "select pontox,pontoy,ds_localizacao " +
		  "	 					from cliente.tb_localizacao";
		StringBuilder campos = new StringBuilder();
		try{
			pstm = AcessoPostGres.getConexao().prepareStatement(marcacao);
		 
			ResultSet rs = pstm.executeQuery();
		 //Mascara para a lista do percurso do Usuario
		 //pontoX,pontoY,descricao:pontoX,pontoY,descricao
			while(rs.next()){
			 campos.append(rs.getString("pontox").toString()+",");
			 campos.append(rs.getString("pontoy").toString()+",");
			 campos.append(rs.getString("ds_localizacao").toString()+",");
			 campos.append(":,");
			}
			}catch(Exception erro){
				erro.printStackTrace();
			}
			finally{
				AcessoPostGres.desconectar();
			}
			return campos.toString();
	}
	
	public String exibePontosAoRedor(String pontoX,String pontoY){
		String marcacao = "select pontox,pontoy,ds_localizacao " +
		  "	 					from cliente.tb_localizacao " +
		  "				   where (pontox <> ? and pontoy <> ?) ";
		
		StringBuilder campos = new StringBuilder();
		try{
			pstm = AcessoPostGres.getConexao().prepareStatement(marcacao);
			pstm.setString(1,pontoX);
			pstm.setString(2,pontoY);
		 
			ResultSet rs = pstm.executeQuery();
		 //Mascara para a lista do percurso do Usuario
		 //pontoX,pontoY,descricao:pontoX,pontoY,descricao
			while(rs.next()){
			 campos.append(rs.getString("pontox").toString()+",");
			 campos.append(rs.getString("pontoy").toString()+",");
			 campos.append(rs.getString("ds_localizacao").toString()+",");
			 campos.append(":,");
			}
			}catch(Exception erro){
				erro.printStackTrace();
			}
			finally{
				AcessoPostGres.desconectar();
			}
			return campos.toString();
	}

}
