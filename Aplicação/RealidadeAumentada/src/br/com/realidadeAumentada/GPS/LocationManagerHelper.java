package br.com.realidadeAumentada.GPS;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationManagerHelper implements LocationListener {

	
    private static double latitude;
    private static double longitude;
    private static Calendar currentTimeStamp = null;

    public LocationManagerHelper(Context context){
    	LocationManager mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,this);
    }
    
    public void onLocationChanged(Location loc) {
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        
        setCurrentTimeStamp(Calendar.getInstance());
    }

    
    public void onProviderDisabled(String provider) { }

    
    public void onProviderEnabled(String provider) { }

    
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

	public static Date getCurrentTimeStamp() {
		Date date = null;
		if(currentTimeStamp != null){
			date = currentTimeStamp.getTime(); 
		}
		return date;
	}

	public void setCurrentTimeStamp(Calendar currentTime) {
		currentTimeStamp = currentTime;
	}

}
