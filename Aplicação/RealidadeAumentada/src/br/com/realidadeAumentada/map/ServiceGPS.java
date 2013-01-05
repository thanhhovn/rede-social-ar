package br.com.realidadeAumentada.map;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
 
public class ServiceGPS extends Service implements LocationListener {
 
    private final Context context;
 
    // flag para status GPS
    boolean isGPSEnabled = false;
 
    // flag para status network
    boolean isNetworkEnabled = false;
 
    // flag para status GPS
    boolean canGetLocation = true;
 
    Location location = null; 
    private double latitude = 0.00;
    private double longitude = 0.00;
 
    // Dist�ncia m�nima para atualizar posi��o GPS em metros
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 0 meters
 
    // Tempo m�nimo entre as atualiza��es em milissegundos 1000 * 60 * 1; // 1 minute
    private static final long MIN_TIME_BW_UPDATES = 0;
 
    protected LocationManager locationManager;
    
    public ServiceGPS(Context context) {
    	this.context = context;
//    	UseGPSActivity use = (UseGPSActivity) context;
        getLocation();
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
    
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            
            String provedor = locationManager.getBestProvider(new Criteria(),true);
            
            // configurando status do GPS
            isGPSEnabled = locationManager.isProviderEnabled(provedor);
            // Se o GPS estiver ativo ir� pegar a lat / long usando a classe ServiceGPS
            if (isGPSEnabled) {
                if (provedor != null) {
                	location= locationManager.getLastKnownLocation(provedor);
                	locationManager.requestLocationUpdates(provedor,MIN_DISTANCE_CHANGE_FOR_UPDATES,MIN_TIME_BW_UPDATES,this);
                	updateWithNewLocation(location);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        if (location != null) {
                        	
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    
    private void updateWithNewLocation(Location location){
    	if(location!=null){
    		latitude=location.getLatitude();
    		longitude=location.getLongitude();
    		
    		// Exibe o local informado pelas Coordenadas do GPS, mas n�o esta funcionando
//    		Geocoder gc=new Geocoder(getApplicationContext(),Locale.getDefault());
//    		try{
//    			List<Address>addresses=gc.getFromLocation(latitude,longitude,1);
//    			StringBuilder sb=new StringBuilder();
//    			if(addresses.size() > 0){
//    				Address address= addresses.get(0);
//    				for(int i=0;i<address.getMaxAddressLineIndex();i++)
//    					sb.append(address.getAddressLine(i)).append("\n");
//    				
//    				sb.append(address.getLocality()).append("\n");
//    				sb.append(address.getPostalCode()).append("\n");
//    				sb.append(address.getCountryName());
//    			}
//    			addressString=sb.toString();
//    		}catch(IOException e){}
    	}else{
    		
    	}
    	stopUsingGPS();    	
    }
 
    
    /**
     * Para o uso do GPS
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(ServiceGPS.this);
        }
        this.canGetLocation = false;
    }
 
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }
 
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }
    
 
    /**
     * Fun��o para Verificar status GPS/wifi
     * @return boolean
     * */
    public boolean canGetLocation() {
        return true;
    }
 
    /**
     *  Fun��o para mostrar as configura��es de di�logo de alerta
����� * Ao pressionar o bot�o Configura��es ser� exibido op��es de configura��o
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
 
        // Setting Dialog Title
        alertDialog.setTitle("Configura��o do GPS");
 
        // Setting Dialog Message
        alertDialog.setMessage("O GPS n�o esta abilitado. Para continuar o servi�o de marca��o voc� deve abilitar o GPS, deseja fazer isso agora?");
 
        // ao precionar o bot�o Settings
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
 
        // ao precionar o bot�o cancelar
        alertDialog.setNegativeButton("N�O", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        alertDialog.show();
    }
    
    public void onLocationChanged(Location location) {
    	updateWithNewLocation(location);
    }
 
    
    public void onProviderDisabled(String provider) {
    	updateWithNewLocation(null);
    }
 
    
    public void onProviderEnabled(String provider) {
    }
 
    
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    
    public IBinder onBind(Intent arg0) {
        return null;
    }

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
 
}