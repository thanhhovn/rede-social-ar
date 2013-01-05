package br.com.realidadeAumentada;


//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;




import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import br.com.realidadeAumentada.webService.SoapClient;


public class WsdlClient extends Activity {

	 private static final String NAMESPACE = "http://hello_webservice/";
	 private static String URL = "http://192.168.42.98:8080/AR_Rede_WebService/services/AR_RedeWS?WSDL"; 
	 private static final String METHOD_NAME = "registrarMarcacaoGPS";
	 private static final String SOAP_ACTION = NAMESPACE+METHOD_NAME ;
 
 private TextView lblResult;
 

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.web_service);

  lblResult = (TextView) findViewById(R.id.result);
 
  inicarWebService();

 }
 
 private void inicarWebService(){
	 
/*	 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 
	  PropertyInfo propInfo=new PropertyInfo();
	  propInfo.name="arg0";
	  propInfo.type= PropertyInfo.STRING_CLASS;
	  propInfo.setValue("Até que termine o dia");
	  request.addProperty(propInfo);
	  //request.addProperty(propInfo, "Até que termine o dia");  
	  SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
	  envelope.setOutputSoapObject(request);
	  HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	  try {
		   androidHttpTransport.call(SOAP_ACTION, envelope);
		   SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
		//   Object  resultsRequestSOAP = (Object) envelope.getResponse();
		    //resultsRequestSOAP.toString();
		   	lblResult.setText(resultsRequestSOAP.toString());
	  } catch (Exception e) {
		  System.out.println(e.getMessage());
	  }
	  
*/
	 try{ 
		 SoapClient client = new SoapClient(SOAP_ACTION,METHOD_NAME, NAMESPACE,URL,false); //create cliente
		 client.addParameter("x","2.4"); //set parameters
		 client.addParameter("y","2.5"); //set parameters
		 client.addParameter("descricao", "Olá mundo!"); //set parameters
		 
		 Object resp = (Object) client.executeCallResponse(); //get response
		 
		 lblResult.setText(resp.toString());
		 }catch(Exception e){
			 System.out.println(e.getCause());
			 System.out.println(e.getMessage());
		 }
 }
 
}  