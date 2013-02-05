package br.com.realidadeAumentada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.cadastroUsuario.Treath;


public class MarcacaoGPSActivity extends Activity implements OnClickListener{

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 0; // in Milliseconds
     
    protected LocationManager locationManager;
     
    private Button confirmar = null;
	private TextView mLatitude;
	private TextView mLongitude;
	private TextView tempo;
    TextView tv;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        
        mLongitude = (TextView) findViewById(R.id.longitude);
        mLatitude = (TextView) findViewById(R.id.latitude);
        tempo = (TextView) findViewById(R.id.tempo);
        
        mLatitude.setText("Aguardando...");
		mLongitude.setText("Aguardando...");
        confirmar = (Button) findViewById(R.id.retrieve_location_button);
        confirmar.setOnClickListener(this);
    }   
 
    protected void showCurrentLocation() {
    	//(int)(location.getLatitude()*1E6)))
    	new LocationManagerHelper(this);
       
        mLatitude.setText(String.valueOf(LocationManagerHelper.getLatitude()));
		mLongitude.setText(String.valueOf(LocationManagerHelper.getLongitude()));
		tempo.setText(String.valueOf(LocationManagerHelper.getCurrentTimeStamp()));
    }

	private boolean isAtivoGPS(){
		boolean status = false;
	    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	    	status = true;
	    }  
	    return status;
	}

//Verifica se o GPS estï¿½ ativo e se nï¿½o estiver exibe a tela para o usuï¿½rio ativar.
	private void statusGPSorAbilitar() {
		if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
		{
		    Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
		    startActivity(myIntent);
		}
	//	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
	//	startActivityForResult(intent, 1);
	}

	public void onClick(View v) {
		if(v == confirmar){
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if(!isAtivoGPS()){
				int duracao = 2000;
				String mensagem = "Você precisa Ativar o GPS para Usar Esta Funcionalidade.";
				Toast toast=Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_SHORT);
				toast.show();
				new Treath(duracao);
				statusGPSorAbilitar();
			}else{
			    showCurrentLocation();
			}
		}	
	}
}