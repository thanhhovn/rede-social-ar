package br.com.realidadeAumentada;

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
import br.com.realidadeAumentada.GPS.LocationManagerHelper;

public class PrincipalActivity extends Activity implements OnClickListener {

 private static final int NOME_DIALOG_ID = 1;
 
 protected LocationManager locationManager;
 
 private String descricaoMarcacao;
 private String mLatitude;
 private String mLongitude;
 private String tempo;
 private boolean OK = false;
 
 private Button editarPerfil;
 private Button visualizarMapa;
 private Button realidadeAumentada;

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.tela_principal);

  realidadeAumentada = (Button) findViewById(R.id.bt_realidadeAumentada);
  visualizarMapa = (Button) findViewById(R.id.bt_visualizarMapa);
  editarPerfil = (Button) findViewById(R.id.bt_editarPerfilUsuario);
  
  realidadeAumentada.setOnClickListener(this);
  visualizarMapa.setOnClickListener(this);
  editarPerfil.setOnClickListener(this);
 }

public void onClick(View v) {
	if(v == editarPerfil){
		Intent intent = new Intent("USUARIO");
		intent.addCategory("PERFIL");
		startActivity(intent);
	}
	if(v == visualizarMapa){
		LocationManagerHelper.setContext(this);
		if(!LocationManagerHelper.isAtivoGPS()){
			LocationManagerHelper.showSettingsAlert();
		}else{
			Intent intent = new Intent("TESTE");
			intent.addCategory("MAPA");
			startActivity(intent);
		}
	}
	if(v == realidadeAumentada){
		LocationManagerHelper.setContext(this);
		if(!LocationManagerHelper.isAtivoGPS()){
			LocationManagerHelper.showSettingsAlert();
		}else{
			Intent intent = new Intent("TESTE");
			intent.addCategory("REALIDADE_AUMENTADA");
			startActivity(intent);
		}
	}
	
}

protected void showCurrentLocation() {
	//(int)(location.getLatitude()*1E6)))
	new LocationManagerHelper(this);
   
    mLatitude = String.valueOf(LocationManagerHelper.getLatitude());
	mLongitude =String.valueOf(LocationManagerHelper.getLongitude());
	tempo = String.valueOf(LocationManagerHelper.getCurrentTimeStamp());
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
								PrincipalActivity.this.removeDialog(NOME_DIALOG_ID);
							}
						}
				);
			dialog.setNegativeButton("Cancelar", new 
					DialogInterface.OnClickListener() {
						@SuppressLint("DefaultLocale")
						public void onClick(DialogInterface dialog,int which) {
							OK = false;
							PrincipalActivity.this.removeDialog(NOME_DIALOG_ID);
						}
					}
			);
			return dialog.create();
		}
	}
	return null;
	}
 
}  