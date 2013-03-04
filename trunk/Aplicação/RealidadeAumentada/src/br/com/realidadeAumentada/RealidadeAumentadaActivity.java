package br.com.realidadeAumentada;

import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
import es.ucm.look.ar.LookAR;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class RealidadeAumentadaActivity extends LookAR implements LocationListener {
	
	Location location;
	private static Integer raio;
	LocationManager manager;
	
	public RealidadeAumentadaActivity(){
		super(true,false,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_main);
//        
        LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        Location location = getLocation();
        
        carregaPontos(location);

/*        float[] rawCoords = transformarCorEsfericaRetangulares(-10.747886f,-37.492441f);
        EntityData data = new EntityData();
        data.setLocation(rawCoords[0],rawCoords[2],rawCoords[1]);
        data.setPropertyValue(EntityFactory.NAME, "Element 1");
        data.setPropertyValue(EntityFactory.COLOR, "green");
        EntityData data1 = new EntityData();
        rawCoords = transformarCorEsfericaRetangulares(-10.743543,-37.490771);
        data1.setLocation(rawCoords[0],rawCoords[2],rawCoords[1]);
        data1.setPropertyValue(EntityFactory.NAME, "Element 2");
        data1.setPropertyValue(EntityFactory.COLOR, "red");
        LookData.getInstance().getDataHandler().addEntity(data);
        LookData.getInstance().getDataHandler().addEntity(data1);
        LookData.getInstance().updateData();
  */      
    }
    
    public void carregaPontos(Location location){
    	try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);
			
			if(getLocation() != location && location != null){
				setLocation(location);
			}
			Double teste1 = getLocation().getLatitude();
        	Double teste2 = getLocation().getLongitude();
			chamaWBS.addParametro(String.valueOf(teste1));
			chamaWBS.addParametro(String.valueOf(teste2));
			if(raio == null){
				chamaWBS.addParametro(String.valueOf(50000));
			}else{
				chamaWBS.addParametro(String.valueOf(raio));
			}

			Object  spo = (Object) chamaWBS.iniciarWBS();
			float[] rawCoords = new float[3];
			EntityData data = null;
			if(spo!=null){
				String retorno = spo.toString();
				String[] listaPercursos = retorno.toString().split(",");
				StringBuilder percursos = new StringBuilder();
				for (int i = 0; i < listaPercursos.length; i++) {
					if(!listaPercursos[i].equals(":")){
						percursos.append(listaPercursos[i]+",");
					}else{
						String[] localList = percursos.toString().split(",");
						float latitudep = Float.valueOf(localList[0]);
						float longitudep = Float.valueOf(localList[1]);
						String descricao = localList[2];
						rawCoords = transformarCorEsfericaRetangulares(latitudep,longitudep);
						
						data = new EntityData();
						data.setLocation(rawCoords[0],rawCoords[2],rawCoords[1]);
						data.setPropertyValue(EntityFactory.NAME,descricao);
						data.setPropertyValue(EntityFactory.COLOR, "green");
						
						LookData.getInstance().getDataHandler().addEntity(data);
						percursos = new StringBuilder();
					}
				}
				LookData.getInstance().updateData();				
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
    }
    
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
		if(this.location != null){
			return this.location;
		}
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		setLocation(location);
        return location;
	}

    @Override
    protected void onDestroy() {
    	if(this.manager != null){
    		this.manager.removeUpdates(this);
    	}
    	super.onDestroy();
    }
    
 // TODO Estava causando erro faltau ao alterar o raio de aproximação
	public void onLocationChanged(Location location) {
		manager.removeUpdates(this);
//		if(getLocation() != location && location != null){
//			setLocation(location);
//			carregaPontos(location);
//		}
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
	
	public static Integer getRaio() {
		return raio;
	}

	public static void setRaio(Integer raio) {
		RealidadeAumentadaActivity.raio = raio;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
        
}