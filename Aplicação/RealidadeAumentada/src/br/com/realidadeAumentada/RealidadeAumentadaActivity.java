package br.com.realidadeAumentada;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.maps.Marcador;
import br.com.realidadeAumentada.util.EntityFactory;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
import es.ucm.look.ar.LookAR;
import es.ucm.look.ar.util.LookARUtil;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;

public class RealidadeAumentadaActivity extends LookAR implements LocationListener {

	private static Integer raio;
	private LocationManager manager;
	private static final int NOME_DIALOG_ID = 1;
	private static int profundidade;
	private List<EntityData> marcadoreList = new ArrayList<EntityData>(0);
	
	public RealidadeAumentadaActivity(){
		super(true,true,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup v= this.getHudContainer();
        v.addView(LookARUtil.getView(R.layout.hud, null ));
        v.setClickable(true);
        
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
    
    public void cadastrarMarcacao(View view){
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.dialog_marcacao_coordenadas,(ViewGroup) findViewById(R.id.layoutMarcacaCoordenada));
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(RealidadeAumentadaActivity.this);
		dialog.setTitle("Marcando um Ponto Geográfico");
		dialog.setView(layout);
		
		final EditText editDescricaoMarcacao = (EditText) layout.findViewById(R.id.et_DescricaoMarcacao);
		final EditText editTituloMarcacao = (EditText) layout.findViewById(R.id.et_DescricaoTitulo);
					dialog.setPositiveButton("OK", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										
										String tituloMarcacao	= editTituloMarcacao.getText().toString();
										String descricaoMarcacao	= editDescricaoMarcacao.getText().toString();
										Location location = LocationManagerHelper.getLocation();
										if (location != null){
											boolean cadastrou = cadastrarMarcacao(tituloMarcacao,descricaoMarcacao,location);
											if(cadastrou){
//												map.invalidate();
												Toast.makeText(RealidadeAumentadaActivity.this,"Marcação Cadastrada",Toast.LENGTH_LONG).show();
											}else{
												Toast.makeText(RealidadeAumentadaActivity.this,"Não Foi Possível se Conectar com o Servidor",Toast.LENGTH_LONG).show();
											}
											RealidadeAumentadaActivity.this.removeDialog(NOME_DIALOG_ID);
										}
									}
								}
						);
					dialog.setNegativeButton("Cancelar", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										RealidadeAumentadaActivity.this.removeDialog(NOME_DIALOG_ID);
									}
								}
					);
			AlertDialog alert = dialog.create();
			alert.show();

    }
    
    private boolean cadastrarMarcacao(String titulo,String descricao,Location location){
		boolean teveSucesso = false;
		if(descricao != null && descricao.length() > 0){
			try{
				MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
				chamaWBS.setMetodo(MetodosWBS.GRAVAR_MARCACAO_GPS);
				
				chamaWBS.addParametro(String.valueOf(Usuario.getUsuario_id()));
				chamaWBS.addParametro(String.valueOf(location.getLatitude()));
				chamaWBS.addParametro(String.valueOf(location.getLongitude()));
				
				chamaWBS.addParametro(titulo);
				chamaWBS.addParametro(descricao);
				Object  spo = (Object) chamaWBS.iniciarWBS();
				if(spo.equals("ERRO")){
					return false;
				}
				LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
				EntityData data = null;
				if(spo!=null){
					String idMarcador = spo.toString();
					String usuario = Usuario.getUsuario_id();
					Marcador marcacao = new Marcador(location.getLatitude(),location.getLongitude(),titulo,descricao,usuario,idMarcador);
					Usuario.addMarcador(marcacao);
					data = new EntityData();
    				float x = EntityFactory.distanciaEntrePonto(location.getLatitude(),location.getLongitude());
    				data.setLocation((-x),0,profundidade+2);
    				data.setPropertyValue(EntityFactory.NAME,titulo);
    				data.setPropertyValue(EntityFactory.COLOR,"green");  
					marcadoreList.add(data);
					carregaAtualizaMarcadores(marcadoreList);
					profundidade++;
					teveSucesso = true;
				}
			 }catch(Exception e){
				 System.out.println(e.getMessage());
			 }
		}
		return teveSucesso;
	}
    
    public void carregaPontos(Location location){
    	try{
    		marcadoreList = new ArrayList<EntityData>(0);
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
					chamaWBS.addParametro(String.valueOf(10)); // precisão de 5 metros
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
		    				data.setLocation((-x),0,profundidade+2);
		    				data.setPropertyValue(EntityFactory.NAME,descricao);
		    				data.setPropertyValue(EntityFactory.COLOR,"green");  
		    				marcadoreList.add(data);
							percursos = new StringBuilder();
							profundidade++;
						}
					}
					carregaAtualizaMarcadores(marcadoreList);
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
	
    private void carregaAtualizaMarcadores(List<EntityData> marcadores){
    	Iterator marcador = marcadores.iterator();
    	while(marcador.hasNext()){
    		EntityData data = (EntityData) marcador.next();
    		LookData.getInstance().getDataHandler().addEntity(data);
    	}
    	LookData.getInstance().updateData();
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