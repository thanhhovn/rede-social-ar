package br.com.realidadeAumentada;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;
import br.com.realidadeAumentada.maps.CustonOverlay;
import br.com.realidadeAumentada.maps.ItemOverlay;
import br.com.realidadeAumentada.maps.LocalOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class TesteMapa extends  MapActivity {
	MapView map;
	MapController controller;
	LocalOverlay mlo;

	//definição das constantes utilizadas na criação do menu
	private static final int TIPOS_VISAO = 0;
	private static final int STREETVIEW = 1;
	private static final int TRAFFIC = 2;
	private static final int SATELLITE = 3;
	private static final int TODOS = 4;
	private static final int RAIO = 5;
	
	private static int TODOS_HABILITADOS = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_mapa);
        map = (MapView) findViewById(R.id.mapview);
        map.setClickable(true) ;
        
        mlo = new LocalOverlay(this,map);        
        //List<Overlay> overlays = map.getOverlays();
        Drawable imagemPadrao = this.getResources().getDrawable(R.drawable.ic_launcher);
        ItemOverlay markers= new ItemOverlay(imagemPadrao,this);
        if(markers.carregaItensMapa()){
        	map.getOverlays().add(markers);
        }

        //mc.zoomToSpan(markers.getLatSpanE6(), markers.getLonSpanE6());
        CustonOverlay overlay = new CustonOverlay(this);
        map.getOverlays().add(overlay);
        map.getOverlays().add(mlo);

        controller = map.getController();
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        int latitude, longitude;
        if (location != null){
        	//Converte para micrograus
        	latitude =  (int)(location.getLatitude() * 1000000);
        	longitude = (int)(location.getLongitude() * 1000000);
        	controller.setCenter(new GeoPoint(latitude,longitude));
        } else {
        //	controller.setCenter(LocalOverlay.newPonto());
        }
        controller.setZoom(15);
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
            MenuItem menuRaio = menu.add(RAIO, RAIO, 0, "Mudar a Área de Cobertura");
             
            //define uma tecla de atalho para o menu, nesse caso a 
            //tecla de atalho é a letra "F"
            //menuFormatar.setShortcut('0', 'F');
             
            //caso seja necessário desabilitar um menu
            //menu.findItem(CONTANTE).setEnabled(false);
        	
            menuVisaoMapa.add(TIPOS_VISAO, TODOS, 0, "TODOS");
            menuVisaoMapa.add(TIPOS_VISAO, STREETVIEW, 1, "StreetView");
            menuVisaoMapa.add(TIPOS_VISAO, SATELLITE, 2, "Satellite");
            menuVisaoMapa.add(TIPOS_VISAO, TRAFFIC, 3, "Traffic");
            
            menuVisaoMapa.setIcon(R.drawable.add_new_item);
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
            case RAIO:{ 
                trace("Você selecionou o menu raio");
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
