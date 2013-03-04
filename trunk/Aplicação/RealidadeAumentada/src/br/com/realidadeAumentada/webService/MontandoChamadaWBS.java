package br.com.realidadeAumentada.webService;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

public class MontandoChamadaWBS {

	private static final String NAMESPACE = "http://hello_webservice/";
	 private static String URL = "http://192.168.42.98:8080/Servidor_WebService_AR_Rede/services/AR_RedeWS?WSDL"; 
	 private String METHOD_NAME = "";
	 private String SOAP_ACTION = "";
	 private List<String> parametros = new ArrayList<String>(0);
	
	 public MontandoChamadaWBS(){}
	 /**
	  * 
	  * @param Informe a ação do WBS
	  */
	 public void setSoap_Action(String metodo){
		 SOAP_ACTION = NAMESPACE+metodo;
	 }
	 
	 public void addParametro(String parametro){
		 if(parametro != null){
			 parametros.add(parametro);
		 }
	 }
	 
	 /**
	  * 
	  * @param Nome do Método a ser chamado
	  */
	 public void setMetodo(String nomeMetodo){
		 METHOD_NAME = nomeMetodo;
	 }
	  
	 public Object iniciarWBS(){
		 Object spo = null;
		 
				 SoapClient client = new SoapClient(SOAP_ACTION,METHOD_NAME, NAMESPACE,URL,false);
				 if(parametros != null && parametros.size() != 0){
					 int x = 0;
					 for (String parametro : parametros) {
						 client.addParameter(String.valueOf(x++),parametro);
					 }
				 }
				 try {
					spo = (Object) client.executeCallResponse();
				} catch (Exception e) {
					spo = new String("ERRO");
				}
		 return spo;
	 }
}
