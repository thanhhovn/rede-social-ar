package br.com.realidadeAumentada;

import java.util.Calendar;
import java.util.Date;

import br.com.realidadeAumentada.cadastroUsuario.Treath;
import br.com.realidadeAumentada.map.LocationManagerHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class UseGPSActivity1 extends Activity implements OnClickListener{

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
     
    protected LocationManager locationManager;
     
    private Button confirmar = null;
    
    TextView tv;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_gps);
        
        confirmar = (Button) findViewById(R.id.retrieve_location_button);
        confirmar.setOnClickListener(this);
         
       locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     
        
    }   
 
    protected void showCurrentLocation() {
 
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
 
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(UseGPSActivity1.this, message,
                    Toast.LENGTH_LONG).show();
        }
 
    }  
 
    private class MyLocationListener implements LocationListener {
 
        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(UseGPSActivity1.this, message, Toast.LENGTH_LONG).show();
            
         /*    Calendar currentTimeStamp = Calendar.getInstance();
				            if (location != null) {
					            Log.d("LOCATION CHANGED", location.getLatitude() + "");
					            Log.d("LOCATION CHANGED", location.getLongitude() + "");
					            String str = "\n CurrentLocation: "+
					            "\n Latitude: "+ location.getLatitude() + 
					            "\n Longitude: " + location.getLongitude() + 
					            "\n Accuracy: " + location.getAccuracy() + 
					            "\n CurrentTimeStamp "+ currentTimeStamp.getTime();         
					              Toast.makeText(UseGPSActivity.this,str,Toast.LENGTH_LONG).show();
					              tv.append(str); 
				            }
				            */
        }
 
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(UseGPSActivity1.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }
 
        public void onProviderDisabled(String s) {
            Toast.makeText(UseGPSActivity1.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }
 
        public void onProviderEnabled(String s) {
            Toast.makeText(UseGPSActivity1.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }
 
    }
 

//	@Override
//	public void onCreate(Bundle savedInstanceState){
//	super.onCreate(savedInstanceState);
//	setContentView(R.layout.main);
//	
//	statusGPSorAbilitar();
//	TextView tv = new TextView(this);
//
//    LocationManager mlocManager = 
//                    (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//    LocationListener mlocListener = new LocationManagerHelper();
//
//    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
//            mlocListener);
//
//    if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            tv.append("Latitude:- " + LocationManagerHelper.getLatitude()
//                    + '\n');
//            tv.append("Longitude:- " + LocationManagerHelper.getLongitude()
//                    + '\n');
//    } else {
//        tv.setText("GPS is not turned on...");
//    }
//
//    /** set the content view to the TextView */
//    setContentView(tv);
//	}

private boolean isAtivoGPS(){
	boolean status = false;
	LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// pegar latitude e Longitude
    	status = true;
    } else {
		// GPS está Desativado.
    	
	} 
    return status;
}

//Verifica se o GPS está ativo e se não estiver exibe a tela para o usuário ativar.
private void statusGPSorAbilitar() {
	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
	if(!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
	{
	    Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
	    startActivity(myIntent);
	}
//	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
//	startActivityForResult(intent, 1);
}

//verifica se gps está destivado e ativa-0
private void turnGPSOnOff(){
String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
if(!provider.contains("gps")){
	  final Intent intent = new Intent();
	  intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	  intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
	  intent.setData(Uri.parse("3")); //3 - código do gps
	  sendBroadcast(intent);
	  
  }
  //Toast.makeText(this, "Your GPS is Enabled",Toast.LENGTH_SHORT).show();
}

public void onClick(View v) {
	if(v == confirmar){
		
		if(!isAtivoGPS()){
			int duracao = 2000;
			Toast toast=Toast.makeText(getApplicationContext(),"É preciso Ativar o GPS.",Toast.LENGTH_SHORT);
			toast.show();
			new Treath(duracao);
			statusGPSorAbilitar();
		}
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
	    showCurrentLocation();
	}	
}


}/* End of UseGps Activity */