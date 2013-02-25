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
import br.com.realidadeAumentada.maps.CustonOverlay;
import br.com.realidadeAumentada.maps.ItemOverlay;
import br.com.realidadeAumentada.maps.LocalOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class TesteMapa extends  MapActivity implements LocationListener{
	MapView map;
	MapController controller;
	LocalOverlay mlo;
	Location local;
	LocationManager manager;

	//definição das constantes utilizadas na criação do menu
	private final int TIPOS_VISAO = 0;
	private final int STREETVIEW = 1;
	private final int TRAFFIC = 2;
	private final int SATELLITE = 3;
	private final int TODOS = 4;
	private final int NENHUM = 6;
	private final int MUDAR_APROXIMACAO = 7;
	private final int EXIBE_TODOS_PONTOS = 8;
	private static final int RAIO = 5;
	private static int TODOS_HABILITADOS = 0;
	
	private static final int NOME_DIALOG_ID = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_mapa);
        map = (MapView) findViewById(R.id.mapview);
        map.setClickable(true) ;
        
        mlo = new LocalOverlay(this,map);        
        //List<Overlay> overlays = map.getOverlays();
        Drawable imagemPadrao = this.getResources().getDrawable(R.drawable.ic_launcher);
        ItemOverlay markers= new ItemOverlay(imagemPadrao,this,TesteMapa.this);

        //mc.zoomToSpan(markers.getLatSpanE6(), markers.getLonSpanE6());
        CustonOverlay overlay = new CustonOverlay(this);

        controller = map.getController();
        int latitude = 0;
        int longitude = 0;
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,this);
        Location location = getLocation();
        if (location != null){
        	//Converte para micrograus
        	latitude =  (int)(location.getLatitude() * 1000000);
        	longitude = (int)(location.getLongitude() * 1000000);
        	
        	controller.setCenter(new GeoPoint(latitude,longitude));
        	controller.animateTo(new GeoPoint(latitude,longitude));
        } else {
        //	controller.setCenter(LocalOverlay.newPonto());
        }
        if(markers.carregaTodosItensMapa(location,null)){
        	map.getOverlays().add(markers);
        }else{
        	map.getOverlays().add(overlay);
        }
        map.getOverlays().add(mlo);
        
        controller.setZoom(16);
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
	
	public void abilitarControladores(boolean on){
    	map.setStreetView(on);
    	map.setTraffic(on);
    	map.setSatellite(on);
    	TODOS_HABILITADOS = 1;
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
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	try{
    		//cria o menu e submenus
            SubMenu menuVisaoMapa = menu.addSubMenu(TIPOS_VISAO, 0, 0, "Selecione o Modo de Visualisação do Mapa");
            SubMenu menuRaio = menu.addSubMenu(RAIO,0, 0, "Mudar a Área de Cobertura");
             
            //define uma tecla de atalho para o menu, nesse caso a 
            //tecla de atalho é a letra "F"
            //menuFormatar.setShortcut('0', 'F');
             
            //caso seja necessário desabilitar um menu
            //menu.findItem(CONTANTE).setEnabled(false);
        	
            menuVisaoMapa.add(TIPOS_VISAO, NENHUM, 0, "Nenhum");
            menuVisaoMapa.add(TIPOS_VISAO, TODOS, 1, "TODOS");
            menuVisaoMapa.add(TIPOS_VISAO, STREETVIEW, 2, "StreetView");
            menuVisaoMapa.add(TIPOS_VISAO, SATELLITE, 3, "Satellite");
            menuVisaoMapa.add(TIPOS_VISAO, TRAFFIC, 4, "Traffic");
            menuVisaoMapa.setIcon(R.drawable.add_new_item);
            
            menuRaio.add(RAIO,MUDAR_APROXIMACAO,0,"Alterar Distância de Aproximação");
            menuRaio.add(RAIO,EXIBE_TODOS_PONTOS,1,"Exibir Todos os Pontos");
            menuRaio.setIcon(R.drawable.icon);
            //caso seja necessário desabilitar um subitem
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
            case TODOS:{     
            	if(TODOS_HABILITADOS == 1){
            		abilitarControladores(false);
            		TODOS_HABILITADOS = 0;
            	}
            	else abilitarControladores(true);
                break;}
            case NENHUM:{ 
                abilitarControladores(false);
              break;}
            case STREETVIEW:{ 
                  if(map.isStreetView())
                	  map.setStreetView(false);
                  else
                	  map.setStreetView(true);
                	  
                break;}
            case TRAFFIC:{ 
                	if(map.isTraffic())
                		map.setTraffic(false);
                	else
                		map.setTraffic(true);
                break;}
            case SATELLITE:{ 
                	if(map.isSatellite())
                		map.setSatellite(false);
                	else
                		map.setSatellite(true);
                break;}
            case MUDAR_APROXIMACAO:{ 
            	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        		final View layout = inflater.inflate(R.layout.dialog_mudar_raio,(ViewGroup) findViewById(R.id.layoutMudarPrecisaoRaio));
        		
        		AlertDialog.Builder dialog = new AlertDialog.Builder(TesteMapa.this);
        		dialog.setTitle("Alterando a Precisão do Raio (Em Metros)");
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
        											Toast.makeText(TesteMapa.this.getBaseContext(),"Pontos proximos com Aproximação de "+raio+" Metros",Toast.LENGTH_LONG).show();
        										}else{
        											Toast.makeText(TesteMapa.this.getBaseContext(),"Não Foi Possível se Conectar com o Servidor",Toast.LENGTH_LONG).show();
        										}
        										TesteMapa.this.removeDialog(NOME_DIALOG_ID);
        									}
        								}
        						);
        					dialog.setNegativeButton("Cancelar", new 
        								DialogInterface.OnClickListener() {
        									@SuppressLint("DefaultLocale")
        									public void onClick(DialogInterface dialog,int which) {
        										TesteMapa.this.removeDialog(NOME_DIALOG_ID);
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
    	mlo.enableCompass();
    	mlo.enableMyLocation();
    	map.setBuiltInZoomControls(true);
    	super.onResume();
    }
    
    @Override
    protected void onRestart() {
    	mlo.disableCompass();
    	mlo.disableMyLocation();
    	super.onRestart();
    }
    
    @Override
    protected void onDestroy() {
    	if(this.manager != null){
    		this.manager.removeUpdates(this);
    	}
    	super.onDestroy();
    }

    // Captura coordenadas e Descrição do Marcador fornecido pelo Usuário
    public void cadastraMarcacao(){
    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.dialog_marcacao_coordenadas,(ViewGroup) findViewById(R.id.layoutMarcacaCoordenada));
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(TesteMapa.this);
		dialog.setTitle("Marcando um Ponto Geográfico");
		dialog.setView(layout);
		
		final EditText editNome = (EditText) layout.findViewById(R.id.et_DescricaoPosicao);
					dialog.setPositiveButton("OK", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										String nome	= editNome.getText().toString();
											if(ItemOverlay.getOverlay().cadastrarPonto(nome)){
												map.invalidate();
												Toast.makeText(TesteMapa.this,"Marcação Cadastrada",Toast.LENGTH_LONG).show();
											}else{
												Toast.makeText(TesteMapa.this,"Não Foi Possível se Conectar com o Servidor",Toast.LENGTH_LONG).show();
											}
										TesteMapa.this.removeDialog(NOME_DIALOG_ID);
									}
								}
						);
					dialog.setNegativeButton("Cancelar", new 
								DialogInterface.OnClickListener() {
									@SuppressLint("DefaultLocale")
									public void onClick(DialogInterface dialog,int which) {
										TesteMapa.this.removeDialog(NOME_DIALOG_ID);
									}
								}
					);
			AlertDialog alert = dialog.create();
			alert.show();
    }

	public void onLocationChanged(Location location) {
		manager.removeUpdates(this);
		if(this.local != location && location != null){
			this.local = location;
			ItemOverlay.getOverlay().carregaItensAoRedorMapa(location);
			map.invalidate();
			Toast.makeText(TesteMapa.this.getBaseContext(),"Coordenada Atualizada.",Toast.LENGTH_LONG).show();
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
