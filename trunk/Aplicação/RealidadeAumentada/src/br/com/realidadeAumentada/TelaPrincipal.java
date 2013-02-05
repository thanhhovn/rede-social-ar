package br.com.realidadeAumentada;


//import org.ksoap2.SoapEnvelope;
//import org.ksoap2.serialization.PropertyInfo;
//import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
//import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.HttpTransportSE;




import org.ksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.cadastroUsuario.Treath;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;


public class TelaPrincipal extends Activity implements OnClickListener {

 private static final int NOME_DIALOG_ID = 1;
 
 protected LocationManager locationManager;
 private EditText alertNomeUsuario = null;
 private String descricaoMarcacao;
 private Button marcacao;
 private String mLatitude;
 private String mLongitude;
 private String tempo;
 private boolean OK = false;
 
 private Button editarPerfil;

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.tela_principal);

  marcacao = (Button) findViewById(R.id.bt_marcacaoUsuarioGPS);
  editarPerfil = (Button) findViewById(R.id.bt_editarPerfilUsuario);
  
  marcacao.setOnClickListener(this);
  editarPerfil.setOnClickListener(this);

 }
 
 private void inicarWebService(){
	 try{ 
		 MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
		 chamaWBS.setMetodo(MetodosWBS.GRAVAR_MARCACAO_GPS);
		 
		 chamaWBS.addParametro("4");
		 chamaWBS.addParametro(mLatitude);
		 chamaWBS.addParametro(mLongitude);
		 chamaWBS.addParametro(descricaoMarcacao);
		 
		  SoapObject  spo = (SoapObject ) chamaWBS.iniciarWBS();
		  // Recupera informa��o
		  //spo.getProperty("cod_filme")
		  if(spo!=null){
		  }
		 }catch(Exception e){
			 System.out.println(e.getCause());
			 System.out.println(e.getMessage());
		 }
 }

public void onClick(View v) {
	if(v == editarPerfil){
		Intent intent = new Intent("USUARIO");
		intent.addCategory("PERFIL");
		startActivity(intent);
	}
	if(v == marcacao){
		descricaoMarcacao = "UFS";
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if(!isAtivoGPS()){
			int duracao = 2000;
			String mensagem = "Voc� precisa Ativar o GPS para Usar Esta Funcionalidade.";
			Toast toast=Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_SHORT);
			toast.show();
			new Treath(duracao);
			statusGPSorAbilitar();
		}else{
//			showDialog(NOME_DIALOG_ID);
			if(descricaoMarcacao != null && descricaoMarcacao.length() > 0){
				showCurrentLocation();
				inicarWebService();
			}
		}
		descricaoMarcacao = null;
	}
	
	
}

protected void showCurrentLocation() {
	//(int)(location.getLatitude()*1E6)))
	new LocationManagerHelper(this);
   
    mLatitude = String.valueOf(LocationManagerHelper.getLatitude());
	mLongitude =String.valueOf(LocationManagerHelper.getLongitude());
	tempo = String.valueOf(LocationManagerHelper.getCurrentTimeStamp());
}

private boolean isAtivoGPS(){
	boolean status = false;
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    	status = true;
    }  
    return status;
}

//Verifica se o GPS est� ativo e se n�o estiver exibe a tela para o usu�rio ativar.
private void statusGPSorAbilitar() {
	if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
	{
	    Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
	    startActivity(myIntent);
	}
//	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
//	startActivityForResult(intent, 1);
}


@Override
	protected Dialog onCreateDialog(int id) {
	switch (id) {
		case (NOME_DIALOG_ID) :{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.alerta_nome,(ViewGroup) findViewById(R.id.layoutAlertaNome));
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Nome");
			dialog.setView(layout);

			final EditText editNome = (EditText) layout.findViewById(R.id.et_AlertaNomeUsuario);

			dialog.setPositiveButton("OK", new 
						DialogInterface.OnClickListener() {
							@SuppressLint("DefaultLocale")
							public void onClick(DialogInterface dialog,int which) {
								
								String nome	= editNome.getText().toString();
								if(nome != null && nome.length() > 0){
									descricaoMarcacao = nome.toUpperCase();
								    OK = true;							
								}
								TelaPrincipal.this.removeDialog(NOME_DIALOG_ID);
							}
						}
				);
			dialog.setNegativeButton("Cancelar", new 
					DialogInterface.OnClickListener() {
						@SuppressLint("DefaultLocale")
						public void onClick(DialogInterface dialog,int which) {
							OK = false;
							TelaPrincipal.this.removeDialog(NOME_DIALOG_ID);
						}
					}
			);
			return dialog.create();
		}
	}
	return null;
	}
 
}  