package br.com.realidadeAumentada;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.map.MyLocation;

import com.google.android.maps.GeoPoint;


public class PassGPSActivity extends Activity {
    /** Called when the activity is first created. */
	private Handler mHandler = new Handler();
	private TextView mLatitude;
	private TextView mLongitude;
	private MyLocation mMyLocation;
	
	
    @SuppressWarnings("static-access")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
    
        mLatitude = (TextView) findViewById(R.id.latitude);
        mLongitude = (TextView) findViewById(R.id.longitude);
        
        mMyLocation = new MyLocation(this);
        mMyLocation.turnGPSOnOff();
        try {
        	// Foi necessário adicionar uma Thread e colocá-la para dormir para atrasar o processo
        	// Isso foi feito para corrigir o problema que ocorria durante a ativação do GPS
        	// Antes se o GPS não estivesse abilitado a aplicação parava.
			new Thread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	Runnable runnable = new Runnable() {
        	public void run() {
        		mHandler.postDelayed(new Runnable() {
					public void run() {
						GeoPoint geoPoint = mMyLocation.getMyLocation();
						mLatitude.setText(geoPoint.getLatitudeE6()+"");
						mLongitude.setText(geoPoint.getLongitudeE6()+"");
					}
				},1000);
        	}
        };
        mMyLocation.runOnFirstFix(runnable);
        mMyLocation.runOnLocationUpdate(runnable);
        mMyLocation.enableMyLocation();
    }
	
	
	protected void onResume() {
		mMyLocation.enableMyLocation();
		mMyLocation.turnGPSOnOff();
		super.onResume();
	}
	
	
	protected void onPause() {
		mMyLocation.disableMyLocation();
		mMyLocation.turnGPSOnOff();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	
	public void alert(String mensagem,double lat,double longi){
    	Toast.makeText(getApplicationContext(), mensagem +"\n Latitude" +((int)lat)+ "\n Longitude: " + ((int)longi), Toast.LENGTH_LONG).show();
    }
    
}