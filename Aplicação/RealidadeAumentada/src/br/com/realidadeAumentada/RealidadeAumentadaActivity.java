package br.com.realidadeAumentada;


import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.util.EntityFactory;
import es.ucm.look.ar.LookAR;
import es.ucm.look.ar.util.LookARUtil;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ViewGroup;

public class RealidadeAumentadaActivity extends LookAR implements LocationListener {

	private static Integer raio;
	private LocationManager manager;
	
	public RealidadeAumentadaActivity(){
		super(true,true,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_main);
//        
        ViewGroup v= this.getHudContainer();
        v.addView(LookARUtil.getView(R.layout.hud, null ));
        
        LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
       
        LocationManagerHelper.setContext(this); 
		Location location = LocationManagerHelper.getLocation();
		if (location != null){
			LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
			carregaPontos(location);
        } else {}
        

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
    
    public float distanciaEntrePonto(Double bx,Double by){
		Double origenX = EntityFactory.origenX;
		Double origenY = EntityFactory.origenY;
		Double origenXporax =  Math.pow((origenX-bx),2);
		Double origenYporay =  Math.pow((origenY-by),2);
		Double distancia = Math.sqrt(origenXporax+origenYporay);
		double teta = Math.cos(origenX)/Math.sin(origenY);
		Double angulo =  (distancia*Math.sin(teta));
	return angulo.floatValue();
}
    
    public float distanciaEntrePonto(Double origenX,Double origenY,Double bx,Double by){
    	Double origenXporax =  Math.sqrt((Math.abs(origenX)-Math.abs(bx)));
    	Double origenYporay =  Math.sqrt((Math.abs(origenY)-Math.abs(by)));
    	Double distancia = Math.sqrt(origenXporax+origenYporay);
    	return distancia.floatValue();
    }
    
    public void carregaPontos(Location location){
    	try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);
//			Location local = LocationManagerHelper.getLocation();
			manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
	        Location local = getLocation();
			if(local != null){
				Double lat = local.getLatitude();
	        	Double longi = local.getLongitude();
				chamaWBS.addParametro(String.valueOf(lat));
				chamaWBS.addParametro(String.valueOf(longi));
				if(raio == null){
					chamaWBS.addParametro(String.valueOf(5)); // precisão de 5 metros
				}else{
					chamaWBS.addParametro(String.valueOf(raio));
				}
				chamaWBS.addParametro(Usuario.getUsuario_id());

				Object  spo = (Object) chamaWBS.iniciarWBS();
	            LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
				EntityData data = null;
				if(spo!=null){
					EntityFactory.origenX = lat;
					EntityFactory.origenY = longi;
					
					// Esta apontando os pontos para a direção errada
					EntityFactory.transformarCorEsfericaRetangulares();
					String retorno = spo.toString();
					String[] listaPercursos = retorno.toString().split(",");
					StringBuilder percursos = new StringBuilder();
					int contadorX = 0;
					for (int i = 0; i < listaPercursos.length; i++) {
						if(!listaPercursos[i].equals(":")){
							percursos.append(listaPercursos[i]+",");
						}else{
							String[] localList = percursos.toString().split(",");
							String descricao = localList[2];
							Double latitude  = Double.valueOf(localList[0]);
		    				Double longitude = Double.valueOf(localList[1]);
		    				data = new EntityData();
		    				float x = EntityFactory.distanciaEntrePonto(latitude,longitude);
//		    				float x = distanciaEntrePonto(lat,longi,latitude,longitude);
		    				data.setLocation((-x),0,contadorX+2);
		    				data.setPropertyValue(EntityFactory.NAME,descricao);
		    				data.setPropertyValue(EntityFactory.COLOR,"green");    
		    				LookData.getInstance().getDataHandler().addEntity(data);
							percursos = new StringBuilder();
							contadorX++;
						}
					}
					LookData.getInstance().updateData();				
				}
			}
			}catch(Exception e){
			 System.out.println(e.getMessage());
		 }
    }
    
	private Location getLocation(){
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
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
	
	public static Integer getRaio() {
		return raio;
	}

	public static void setRaio(Integer raio) {
		RealidadeAumentadaActivity.raio = raio;
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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