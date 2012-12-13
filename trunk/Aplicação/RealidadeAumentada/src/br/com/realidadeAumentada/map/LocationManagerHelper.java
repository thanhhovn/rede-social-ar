package br.com.realidadeAumentada.map;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationManagerHelper implements LocationListener {

    private static double latitude;
    private static double longitude;

    
    public void onLocationChanged(Location loc) {
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
    }

    
    public void onProviderDisabled(String provider) { }

    
    public void onProviderEnabled(String provider) { }

    
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

}
