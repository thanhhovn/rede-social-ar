package br.com.realidadeAumentada;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.map.MyLocation;

import com.google.android.maps.GeoPoint;


public class UseGPSActivity extends Activity {
    /** Called when the activity is first created. */
	private Handler mHandler = new Handler();
	private TextView mLatitude;
	private TextView mLongitude;
	private MyLocation mMyLocation;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
    
        mLatitude = (TextView) findViewById(R.id.latitude);
        mLongitude = (TextView) findViewById(R.id.longitude);
        
        mMyLocation = new MyLocation(this);
        Runnable runnable = new Runnable() {
        	public void run() {
        		mHandler.post(new Runnable() {
					public void run() {
						GeoPoint geoPoint = mMyLocation.getMyLocation();
						mLatitude.setText(geoPoint.getLatitudeE6()+"");
						mLongitude.setText(geoPoint.getLongitudeE6()+"");
					}
				});
        	}
        	
        	
        };
        mMyLocation.runOnFirstFix(runnable);
        mMyLocation.runOnLocationUpdate(runnable);
        mMyLocation.enableMyLocation();
    }
	
	
	protected void onResume() {
		mMyLocation.enableMyLocation();
		super.onResume();
	}
	
	
	protected void onPause() {
		mMyLocation.disableMyLocation();
		super.onPause();
	}

	
	public void alert(String mensagem,double lat,double longi){
    	Toast.makeText(getApplicationContext(), mensagem +"\n Latitude" +((int)lat)+ "\n Longitude: " + ((int)longi), Toast.LENGTH_LONG).show();
    }
    
}