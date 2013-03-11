package br.com.realidadeAumentada;


import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
import br.com.realidadeAumentada.util.EntityFactory;
import es.ucm.look.ar.LookAR;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class RealidadeAumentadaActivity extends LookAR {

	private static Integer raio;
	
	public RealidadeAumentadaActivity(){
		super(true,false,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_main);
//        
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
    
//    public float distanciaEntrePonto(Double bx,Double by){
//    	Location location = LocationManagerHelper.getLocation();
//    	Double origenX =  location.getLatitude();
//    	Double origenY =  location.getLongitude();
//    	Double origenXporax =  Math.sqrt((origenX-bx));
//    	Double origenYporay =  Math.sqrt((origenY-by));
//    	Double distancia = Math.sqrt(origenXporax+origenYporay);
//    	
//    	return distancia.floatValue();
//    }
    
    public void carregaPontos(Location location){
    	try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);
			Location local = LocationManagerHelper.getLocation();
			if(local != null){
				Double teste1 = local.getLatitude();
	        	Double teste2 = local.getLongitude();
				chamaWBS.addParametro(String.valueOf(teste1));
				chamaWBS.addParametro(String.valueOf(teste2));
				if(raio == null){
					chamaWBS.addParametro(String.valueOf(50000));
				}else{
					chamaWBS.addParametro(String.valueOf(raio));
				}

				Object  spo = (Object) chamaWBS.iniciarWBS();
	            LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
				EntityData data = null;
				if(spo!=null){
					Location l = LocationManagerHelper.getLocation();
					EntityFactory.origenX = l.getLatitude();
					EntityFactory.origenY = l.getLongitude();
					
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
		    				data.setLocation(x,0,contadorX+2);
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
        
}