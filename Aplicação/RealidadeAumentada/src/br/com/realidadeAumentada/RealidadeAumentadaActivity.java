package br.com.realidadeAumentada;

import br.com.realidadeAumentada.maps.ItemOverlay;
import es.ucm.look.ar.LookAR;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import es.ucm.look.locationProvider.map.Mapa;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class RealidadeAumentadaActivity extends LookAR implements LocationListener {
	
	Location local;
	LocationManager manager;
	
	public RealidadeAumentadaActivity(){
		super(true,true,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_main);
//        
        LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,this);
        Location location = getLocation();

        float[] rawCoords = transformarCorEsfericaRetangulares(-10.747886f,-37.492441f);
        
        EntityData data = new EntityData();
        data.setLocation(rawCoords[0],rawCoords[2],rawCoords[1]);
        data.setPropertyValue(EntityFactory.NAME, "Element 1");
        data.setPropertyValue(EntityFactory.COLOR, "green");
        
//        data.getLocation();
        EntityData data1 = new EntityData();
        //-10.74","-37.49"
        
        rawCoords = transformarCorEsfericaRetangulares(-10.743543f,-37.490771f);
//		rawCoords[0] = -10.74f;
//		rawCoords[1] = -37.49f;
		
//		int[] mapCoords = Mapa.toMapScale(rawCoords[0],rawCoords[1]);
		
		
        data1.setLocation(rawCoords[0],rawCoords[2],rawCoords[1]);
        data1.setPropertyValue(EntityFactory.NAME, "Element 2");
        data1.setPropertyValue(EntityFactory.COLOR, "red");
        
        LookData.getInstance().getDataHandler().addEntity(data);
        LookData.getInstance().getDataHandler().addEntity(data1);
        
        LookData.getInstance().updateData();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
    
    public float[] transformarCorEsfericaRetangulares(float x, float y){
    	double raio =  Math.sqrt( (Math.pow(x, 2)+ Math.pow(y, 2)+1) );
    	double teta =  Math.atan2(y,x);
    	double zeta =   Math.atan2(Math.sqrt( (Math.pow(x, 2)+ Math.pow(y, 2)) ),1);
    	
    	x = (float) (raio*Math.sin(teta)*Math.cos(zeta));
    	y = (float) (raio*Math.sin(teta)*Math.sin(zeta));
    	float z = (float) (raio*Math.cos(teta));
    	float[] retorno = {x,y,z};
    	return retorno;
    }
    
    public float[] transformarCorRetangularCilindrica(float x, float y){
    	double p =  Math.sqrt( (Math.pow(x, 2)+ Math.pow(y, 2) ));
    	double teta = Math.cos(x)/Math.sin(y);
    	x = (float) (p*Math.cos(teta));
    	y = (float) (p*Math.sin(teta));
    	float[] retorno = {x,y};
    	return retorno;
    }
    
    private Location getLocation(){
		if(this.local != null){
			return this.local;
		}
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
        return location;
	}

    @Override
    protected void onDestroy() {
    	if(this.manager != null){
    		this.manager.removeUpdates(this);
    	}
    	super.onDestroy();
    }
    
	public void onLocationChanged(Location location) {
		manager.removeUpdates(this);
		if(this.local != location && location != null){
			this.local = location;
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