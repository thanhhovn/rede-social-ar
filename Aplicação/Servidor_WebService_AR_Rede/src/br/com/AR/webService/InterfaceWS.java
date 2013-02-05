package br.com.AR.webService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "HelloWS", 
			targetNamespace = "http://hello_webservice/")
public interface InterfaceWS {
	
	@WebMethod(operationName = "login")
	public String login(@WebParam(name = "email") String email,
						@WebParam(name = "senha") String senha);
	
	@WebMethod(operationName = "registrarMarcacaoUsuarioGPS")
	public String registrarMarcacaoGPS(@WebParam(name = "idUsuario") String idUsuario,
									   @WebParam(name = "pontoX") String pontoX,
									   @WebParam(name = "pontoY") String pontoY,
									   @WebParam(name = "descricaoLocalicao") String descricaoLocalicao);
	@WebMethod(operationName = "percursoUsuario")
	public String percursoUsuario(@WebParam(name = "idUsuario") String idUsuario);
	
	@WebMethod(operationName = "exibePontosAoRedor")
	public String exibePontosAoRedor(@WebParam(name = "pontoX") String pontoX,
									 @WebParam(name = "pontoY") String pontoY);
	
	
	@WebMethod(operationName = "resposta")
	public String resposta();
	
	@WebMethod(operationName = "exibeTodosPontos")
	public String exibeTodosPontos();
	
	@WebMethod(operationName = "getEstados")
	public String getEstados();
	
	@WebMethod(operationName = "getMunicipios")
	public String getMunicipios(@WebParam(name = "codEstado") int codEstado);
	
	@WebMethod(operationName = "cadastrarUsuario")
	public String cadastrarUsuario(@WebParam(name = "aluno") String aluno);
	
}
