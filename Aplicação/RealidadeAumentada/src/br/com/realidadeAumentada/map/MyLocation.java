package br.com.realidadeAumentada.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.google.android.maps.GeoPoint;

public class MyLocation implements LocationListener {
	//private int count;
	private LocationManager locationManager;
	private Context mContext;
	private GeoPoint mMyLocation;
	private volatile boolean mIsMyLocationEnabled;
	private Queue<Runnable> mRunnableFirstFixQueue = new LinkedList<Runnable>();
	private Queue<Runnable> mRunnable = new LinkedList<Runnable>();
	
	private String[] PROVIDERS_NAME = new String[] { LocationManager.GPS_PROVIDER,
			LocationManager.NETWORK_PROVIDER };

	public MyLocation(Context context) {
		mContext = context;
	}

	public void runOnFirstFix(Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("MyLocation Exception: runnable must not be null.");
		}
		mRunnableFirstFixQueue.add(runnable);
	}

	public void runOnLocationUpdate(Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("MyLocation Exception: runnable must not be null");
		}
		mRunnable.add(runnable);
	}
	
    private Criteria getCriteria(){
    	Criteria criteria = new Criteria();
    	criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	criteria.setPowerRequirement(Criteria.POWER_LOW);
    	criteria.setAltitudeRequired(false);
    	criteria.setBearingRequired(false);
    	criteria.setSpeedRequired(true);
    	criteria.setCostAllowed(true);
    	return criteria;
    }
    
    public boolean turnGPSOnOff(){
    	if(!isAtivoGPS()){
	    	String provider = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	    	if(!provider.contains("gps")){
	    		  final Intent intent = new Intent();
	    		  intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	    		  intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
	    		  intent.setData(Uri.parse("3")); //3 - c�digo do gps
	    		  mContext.sendBroadcast(intent);
	    	  }
	    	  //Toast.makeText(this, "Your GPS is Enabled",Toast.LENGTH_SHORT).show();
	    	}
    	return true;
    }
    
    private   boolean isAtivoGPS(){
    	locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	return true;
        }
        return false;
    }

    //Verifica se o GPS est� ativo e se n�o estiver exibe a tela para o usu�rio ativar.
/*    private void abilitarGPS() {
    	    Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
    	    mContext.startActivity(myIntent);

//    	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
//    	startActivityForResult(intent, 1);
    }
    */
	
	public synchronized void enableMyLocation() {
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		String provedor = locationManager.getBestProvider(getCriteria(),true);
		Location[] candidates = new Location[PROVIDERS_NAME.length];
		int count = 0;
		for (String provider : PROVIDERS_NAME) {
			mIsMyLocationEnabled = true;
			locationManager.requestLocationUpdates(provedor, 0, 0, this);
			
			Location location = locationManager.getLastKnownLocation(provedor);
			if(location == null) {
				continue;
			}
			candidates[count++] = location;
		}
		
		Location firstFix = null;
		for (int i = 0; i < count; i++) {
			firstFix = candidates[i];
		}
		if(firstFix != null) {
			onLocationChanged(firstFix);
		}
		
	}
	
	public void disableMyLocation() {
		if(mIsMyLocationEnabled) {
			LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
			locationManager.removeUpdates(this);
			turnGPSOnOff();
		}
	}
	
	public GeoPoint getMyLocation() {
		return mMyLocation;
	}

	public void onLocationChanged(Location location) {
		mMyLocation = new GeoPoint((int)(location.getLatitude()*1E6), (int)(location.getLongitude()*1E6));
		
		//Run on first fix
		Runnable runnable;
		while ((runnable = mRunnableFirstFixQueue.poll()) != null) {
			new Thread(runnable).start();
		}
		
		//Run on any location updates.
		Iterator<Runnable> runnables = mRunnable.iterator();
		while (runnables.hasNext()) {
			Runnable updateRunnable = (Runnable) runnables.next();
			new Thread(updateRunnable).start();
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

}

