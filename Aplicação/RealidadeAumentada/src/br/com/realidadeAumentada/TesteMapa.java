package br.com.realidadeAumentada;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import br.com.realidadeAumentada.maps.LocalOverlay;
import br.com.realidadeAumentada.maps.Ponto;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class TesteMapa extends  MapActivity {
	MapView map;
	MapController controller;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_mapa);
        map = (MapView) findViewById(R.id.mapview);
        map.setClickable(true) ;
        
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        int lat, lng;
        if (location != null){
        	//Converte para micrograus
        	lat = (int)(location.getLatitude() * 1000000);
        	lng = (int)(location.getLongitude() * 1000000);
        } else {
        	lat = -23426118;
        	lng = -51938210;
        }
        Ponto mapCenter = new Ponto(lat,lng,"UFS");
        LocalOverlay mlo = new LocalOverlay(this,map);
      List<LocalOverlay> overlayList = mlo.getListOverlay();
      for (LocalOverlay local : overlayList ) {
      	map.getOverlays().add(local);
		}
        map.getOverlays().add(mlo);
        map.invalidate();
        abilitarControladores(true);
        
        controller = map.getController();
        mlo.enableCompass();
        mlo.enableMyLocation();        
        controller.setCenter(mapCenter);
        controller.setZoom(15);
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void abilitarControladores(boolean on){
    	map.setBuiltInZoomControls(on);
    	map.setStreetView(false);
    	map.setTraffic(false);
    	map.setSatellite(false);
    }
	
	@SuppressWarnings("deprecation")
	public void abilitarStreetView(boolean on){
		map.setStreetView(on);	
    }
    
    public void abilitarTrafico(boolean on){
    	map.setTraffic(on);	
    }
    
    public void abilitarSatelite(boolean on){
    	map.setSatellite(on);	
    }	
	
	
/*	private void recuperaLocalPorCoordenadas(double lat, double log){
		String geoURI = String.format("geo:%f,%f?z=10", -10.7483672f, -37.49291661666667f);
		Uri geo = Uri.parse(geoURI);
		Intent geoMap = new Intent(Intent.ACTION_VIEW, geo); 
		startActivity(geoMap);
	}
*/	
	
/*	private void mapa(){
		Geocoder coder = new Geocoder(getApplicationContext());
		String strLocation = "Campo do Brito";
		try {
			List<Address> geocodeResults = 
			coder.getFromLocationName(strLocation, 1);
			
			Iterator<Address> locations = geocodeResults.iterator();
			while (locations.hasNext()) {
			Address loc = locations.next(); 
			double lat = loc.getLatitude(); 
			double lon = loc.getLongitude(); 
			// TODO: Do something with these coordinates
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

*/
	
	
}
