package br.com.realidadeAumentada.webService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapClient {
	
	private String soapAction;              //nome da ação no webservice
	private String methodName;              //nome do método a ser chamado
	private String namespace;               //namespace
	private String uri;                     //path(wsdl, asmx..)    
	private Boolean dotNet;                 //is .NET?
	private Map<String,Object> parameters;  

	public SoapClient(String soapAction, String methodName, String namespace, String uri, Boolean dotNet){
        setSoapAction(soapAction);
        setMethodName(methodName);
        setNamespace(namespace);
        setUri(uri);
        setDotNet(dotNet);
	}


	public String getSoapAction() {
	        return soapAction;
	}
	
	public void setSoapAction(String soapAction) {
	        this.soapAction = soapAction;
	}
	
	public String getMethodName() {
	        return methodName;
	}
	
	public void setMethodName(String methodName) {
	        this.methodName = methodName;
	}
	
	public String getNamespace() {
	        return namespace;
	}
	
	public void setNamespace(String namespace) {
	        this.namespace = namespace;
	}
	
	public String getUri() {
	        return uri;
	}
	
	public void setUri(String uri) {
	        this.uri = uri;
	}
	
	public Map<String, Object> getParameters() {
	        return parameters;
	}
	
	public void setParameters(Map<String, Object> parameters) {
	        this.parameters = parameters;
	}
	
	public Boolean getDotNet() {
	        return dotNet;
	}
	
	public void setDotNet(Boolean dotNet) {
	        this.dotNet = dotNet;
	}
	
	public void addParameter(String parameterName, Object parameterValue){
	        if(getParameters() == null){
	                this.parameters = new HashMap<String, Object>();
	        }               
	        getParameters().put(parameterName, parameterValue);
	}
	
	public Object executeCallResponse() throws Exception{
	        Object result = null;
	        SoapObject request = new SoapObject(getNamespace(), getMethodName()); //cria o método soap              
	        
	        if(getParameters() != null){
        		Collection<Object> objeto = getParameters().values();
        		Object[] objetos = objeto.toArray();
        		for (int i = objetos.length-1; i >= 0 ; i--) {
  	                Object value = objetos[i];                     
  	                request.addProperty("param"+i, value);  
				}
//                for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
//                        String parameter = property.getKey();  
//                Object value = property.getValue();                     
//                request.addProperty(parameter, value);                  
//                }                                              
	                getParameters().clear();
	        }
	                            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serializa o envelope 
	    envelope.dotNet= getDotNet();
	    envelope.setOutputSoapObject(request); //esta método é encarregado de guardar a resposta em request
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //abre a requisição
	    androidHttpTransport.call(getSoapAction(), envelope);// chama
	    result = envelope.getResponse(); // retorna o resultado            
	        
	        return result;
	}
	
	public void executeCall() throws Exception{
	        SoapObject request = new SoapObject(getNamespace(), getMethodName()); //cria o método soap              
	        
	        if(getParameters() != null){
	                for (Map.Entry<String, Object> property : getParameters().entrySet()) {  
	                        String parameter = property.getKey();  
	                Object value = property.getValue();                     
	                request.addProperty(parameter, value);                  
	                }                                       
	                getParameters().clear();
	        }
	                            
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); //serializa o envelope 
	    envelope.dotNet= getDotNet();
	    HttpTransportSE androidHttpTransport = new HttpTransportSE(getUri()); //abre a requisição
	    androidHttpTransport.call(getSoapAction(), envelope);// chama
	
	}

}
