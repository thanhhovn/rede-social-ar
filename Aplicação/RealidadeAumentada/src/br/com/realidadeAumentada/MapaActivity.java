package br.com.realidadeAumentada;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import br.com.realidadeAumentada.maps.ItemOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MapaActivity extends  MapActivity implements LocationListener{
	MapView map;
	MapController controller;
	Location local;
	LocationManager manager;
	ItemOverlay markers;

	//definiï¿½ï¿½o das constantes utilizadas na criaï¿½ï¿½o do menu
	private final int TIPOS_VISAO = 0;
//	private final int TRAFFIC = 2;
	private final int SATELLITE = 3;
//	private final int TODOS = 4;
	private final int VISUALIZACAO_PADRAO = 6;
	private final int MUDAR_APROXIMACAO = 7;
	private final int EXIBE_TODOS_PONTOS = 8;
	private static final int RAIO = 5;
//	private static int TODOS_HABILITADOS = 0;
	
	private static final int NOME_DIALOG_ID = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        map = (MapView) findViewById(R.id.mapview);
        map.setClickable(true) ;
        
        Drawable imagemPadrao = this.getResources().getDrawable(R.drawable.meu_marcador);
        markers= new ItemOverlay(imagemPadrao,this,MapaActivity.this,map);

        //mc.zoomToSpan(markers.getLatSpanE6(), markers.getLonSpanE6());
     
        controller = map.getController();
        int latitude = 0;
        int longitude = 0;
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        local = getLocation();
        GeoPoint point = null;
        if (local != null){
        	//Converte para micrograus
        	latitude =  (int)(local.getLatitude() * 1000000);
        	longitude = (int)(local.getLongitude() * 1000000);
        	
        	//controller.setCenter(new GeoPoint(latitude,longitude));
        	point = new GeoPoint(latitude,longitude);
        }
        if(markers.carregaTodosItensMapa(local,null)){
        	map.getOverlays().add(markers);
        }
        map.getOverlays().add(markers.getMyLocationOverlay());
//        controller.setZoom(12);
        
        if (savedInstanceState == null) {
			final MapController mc = map.getController();
			if(point != null)
				mc.animateTo(point);
			mc.setZoom(16);
		} else {
			int focused;
			focused = savedInstanceState.getInt("focused", -1);
			if (focused >= 0) {
				markers.setFocus(markers.getItem(focused));
			}
		}
    }

	private Location getLocation(){
		if(this.local != null){
			return this.local;
		}
		Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return location;
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void habilitarControladores(boolean on){
//    	map.setTraffic(on);
    	map.setSatellite(on);
//    	TODOS_HABILITADOS = 1;
    }
	
//    public void habilitarTrafico(boolean on){
//    	map.setTraffic(on);	
//    }
    
    public void habilitarSatelite(boolean on){
    	map.setSatellite(on);	
    }	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	try{
    		//cria o menu e submenus
            SubMenu menuVisaoMapa = menu.addSubMenu(TIPOS_VISAO, 0, 0, "Selecione o Modo de Visualisação do Mapa");
            SubMenu menuRaio = menu.addSubMenu(RAIO,0, 0, "Mudar a área de Cobertura");
             
            //define uma tecla de atalho para o menu, nesse caso a 
            //tecla de atalho ï¿½ a letra "F"
            //menuFormatar.setShortcut('0', 'F');
             
            //caso seja necessï¿½rio desabilitar um menu
            //menu.findItem(CONTANTE).setEnabled(false);
        	
            menuVisaoMapa.add(TIPOS_VISAO, VISUALIZACAO_PADRAO, 0, "Padrão");
//            menuVisaoMapa.add(TIPOS_VISAO, TODOS, 1, "TODOS");
            menuVisaoMapa.add(TIPOS_VISAO, SATELLITE, 1, "Satellite");
//            menuVisaoMapa.add(TIPOS_VISAO, TRAFFIC, 3, "Traffic");
            menuVisaoMapa.setIcon(R.drawable.visualizacao);
            
            menuRaio.add(RAIO,MUDAR_APROXIMACAO,0,"Alterar Distância de Aproximação");
            menuRaio.add(RAIO,EXIBE_TODOS_PONTOS,1,"Exibir Todos os Pontos");
            menuRaio.setIcon(R.drawable.marcacoes_redor);
            //caso seja necessï¿½rio desabilitar um subitem
            //menuArquivo.findItem(CONSTANTE).setEnabled(false);
        }
        catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }            
        return super.onCreateOptionsMenu(menu);      
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case TIPOS_VISAO:{     
            	break;}
//            case TODOS:{     
//            	if(TODOS_HABILITADOS == 1){
//            		habilitarControladores(false);
//            		TODOS_HABILITADOS = 0;
//            	}
//            	else habilitarControladores(true);
//                break;}
            case VISUALIZACAO_PADRAO:{ 
                habilitarControladores(false);
              break;}
//            case TRAFFIC:{ 
//                	if(map.isTraffic())
//                		map.setTraffic(false);
//                	else
//                		map.setTraffic(true);
//                break;}
            case SATELLITE:{ 
                	if(map.isSatellite())
                		map.setSatellite(false);
                	else
                		map.setSatellite(true);
                break;}
            case MUDAR_APROXIMACAO:{ 
            	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        		final View layout = inflater.inflate(R.layout.dialog_mudar_raio,(ViewGroup) findViewById(R.id.layoutMudarPrecisaoRaio));
        		
        		AlertDialog.Builder dialog = new AlertDialog.Builder(MapaActivity.this);
        		dialog.setTitle("Exibir pontos de interesse próximos.");
        		dialog.setView(layout);
        		
        		final EditText editNome = (EditText) layout.findViewById(R.id.et_valorRaio);
        					dialog.setPositiveButton("OK", new 
        								DialogInterface.OnClickListener() {
        									@SuppressLint("DefaultLocale")
        									public void onClick(DialogInterface dialog,int which) {
        										String raio	= editNome.getText().toString();
        										if(ItemOverlay.getOverlay().alterarRaio(raio)){
        											ItemOverlay.getOverlay().carregaItensAoRedorMapa(getLocation());
        											map.invalidate();
        											Toast.makeText(MapaActivity.this.getBaseContext(),"Exibindo pontos de interesse com aproximação de "+raio+" Metros",Toast.LENGTH_LONG).show();
        										}else{
        											Toast.makeText(MapaActivity.this.getBaseContext(),"Não foi possível se conectar com o servidor. Tente mais Tarde!",Toast.LENGTH_LONG).show();
        										}
        										MapaActivity.this.removeDialog(NOME_DIALOG_ID);
        									}
        								}
        						);
        					dialog.setNegativeButton("Cancelar", new 
        								DialogInterface.OnClickListener() {
        									@SuppressLint("DefaultLocale")
        									public void onClick(DialogInterface dialog,int which) {
        										MapaActivity.this.removeDialog(NOME_DIALOG_ID);
        									}
        								}
        					);
        			AlertDialog alert = dialog.create();
        			alert.show();
                break;}   
            case EXIBE_TODOS_PONTOS:{ 
            	ItemOverlay.getOverlay().carregaTodosItensMapa(getLocation(),null);
            break;}
        }
        return true;
    }
    
    private void trace (String msg) 
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }
    
    @Override
    protected void onResume() {
    	markers.enableCompass();
    	markers.enableMyLocation();
    	map.setBuiltInZoomControls(true);
    	super.onResume();
    }
    
    @Override
    protected void onRestart() {
    	markers.disableCompass();
    	markers.disableMyLocation();
    	super.onRestart();
    }
    
    @Override
    protected void onDestroy() {
    	if(this.manager != null){
    		this.manager.removeUpdates(this);
    	}
    	super.onDestroy();
    }
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (markers.getFocus() != null) outState.putInt("focused", markers.getLastFocusedIndex());
		super.onSaveInstanceState(outState);
	}

    // Captura coordenadas e Descriï¿½ï¿½o do Marcador fornecido pelo Usuï¿½rio
    public void cadastraMarcacao(){
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.dialog_marcacao_coordenadas,(ViewGroup) findViewById(R.id.layoutMarcacaCoordenada));
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(MapaActivity.this);
		dialog.setTitle("Cadastrando ponto de interesse");
		dialog.setView(layout);
		
		final EditText editTituloMarcacao = (EditText) layout.findViewById(R.id.et_DescricaoTitulo);
		final EditText editDescricaoMarcacao = (EditText) layout.findViewById(R.id.et_DescricaoMarcacao);
		
					dialog.setPositiveButton("OK", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										String titulo	= editTituloMarcacao.getText().toString();
										String descricao	= editDescricaoMarcacao.getText().toString();
											if(ItemOverlay.getOverlay().cadastrarPonto(titulo,descricao,local)){
												map.invalidate();
												Toast.makeText(MapaActivity.this,"Cadastro da marcação realizado com sucesso!",Toast.LENGTH_LONG).show();
											}else{
												Toast.makeText(MapaActivity.this,"Nãoo foi possível se conectar com o servidor.",Toast.LENGTH_LONG).show();
											}
										MapaActivity.this.removeDialog(NOME_DIALOG_ID);
									}
								}
						);
					dialog.setNegativeButton("Cancelar", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										MapaActivity.this.removeDialog(NOME_DIALOG_ID);
									}
								}
					);
			AlertDialog alert = dialog.create();
			alert.show();
    }

    // TODO Estava causando erro faltau ao alterar o raio de aproximaï¿½ï¿½o
	public void onLocationChanged(Location location) {
		manager.removeUpdates(this);
		if(this.local != location && location != null){
//			this.local = location;
//			ItemOverlay.getOverlay().carregaItensAoRedorMapa(location);
//			map.invalidate();
//			Toast.makeText(TesteMapa.this.getBaseContext(),"Coordenada Atualizada.",Toast.LENGTH_LONG).show();
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
