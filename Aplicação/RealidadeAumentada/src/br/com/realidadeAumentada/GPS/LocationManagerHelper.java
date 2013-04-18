package br.com.realidadeAumentada.GPS;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class LocationManagerHelper implements LocationListener {

	
    private static double latitude;
    private static double longitude;
    private static Calendar currentTimeStamp = null;
    private static Context context = null;
    private static LocationManager mlocManager = null;
    private static Location local;

    public LocationManagerHelper(Context c){
    	context = c;
    	mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,this);
    }
    
	public static void setContext(Context c){
		if(context == null && c != null){
			context = c;
		}
	}
    
    public void onLocationChanged(Location loc) {
    	local = loc;
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        
        setCurrentTimeStamp(Calendar.getInstance());
    }
    
    public static void setLocation(Location loc){
    	if(loc != null){
    		local = loc;	
    	}
    }

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

	public Location getLocation(){
		if(local != null){
			return local;
		}
		Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
	}	
	
	public void setCurrentTimeStamp(Calendar currentTime) {
		currentTimeStamp = currentTime;
	}
	
    public static boolean isAtivoGPS(){
    	mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        	return true;
        }
        return false;
    }
	
    /**
     *  mostra as configuracoes atraves de alerta
     * Ao pressionar o botao Configuracoes sera exibido opcoes de configuracao
     * */
    public static void showSettingsAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("Configuração do GPS");
		alertDialog.setMessage("O GPS não esta habilitado. Para continuar o serviço você tem que habilitar o GPS, deseja fazer isso agora?");

		alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});
		alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog.show();
    }

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeUpdates(){
		 mlocManager.removeUpdates(this);
	}
	
	
}