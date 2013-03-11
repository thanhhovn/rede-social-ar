package br.com.realidadeAumentada.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.MotionEvent;
import android.widget.Toast;
import br.com.realidadeAumentada.MapaActivity;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> itens;
	private Context contexto;
	private static boolean TODOS_PONTOS; 
	private MapaActivity mapa;
	private GeoPoint point;
	private long start;
	private long stop;
	private static Integer raio;
	private static ItemOverlay overlay;
	private static Location location;
	private static MapView mapView;
	private MyLocationOverlay myLocationOverlay;
	

	public ItemOverlay(Drawable defaultMarker, Context contexto, MapaActivity mapa,MapView map) {
		super(boundCenterBottom(defaultMarker)); //Marcação Padrão
		myLocationOverlay = new MyLocationOverlay(contexto, map);
		this.contexto = contexto;
		mapView = map;
		this.mapa = mapa;
		overlay = ItemOverlay.this;
		newInstanceItemOverlay();
	}
	
	private void newInstanceItemOverlay(){
		this.itens = new ArrayList<OverlayItem>(0);
	}
	public void addNewItem(Ponto ponto,String fragmento){
		this.itens.add(new OverlayItem(ponto,ponto.getDescricao(),fragmento));
		populate();
	}
	
	public void removeItem(int index){
		this.itens.remove(index);
		populate();
	}
	
	public boolean carregaTodosItensMapa(Location local,OverlayItem item){
		TODOS_PONTOS = true;
		if(getLocation() != location && location != null){
			setLocation(location);
		}
			return carregaItensMapa(getLocation(),item);
	}
	
	private boolean carregaItensMapa(Location location, OverlayItem item){
		boolean flag = false;
		// OverlayItem().setMarker(Drawable); permite que cada item possua uma imagem diferente.
// Dados de Teste		
/*		itens.add(new OverlayItem(new Ponto(-10.7483672,-37.49421661666667),"PE"," "));
		itens.add(new OverlayItem(new Ponto(-10.7483373,-37.49291661666657),"SP"," "));
		itens.add(new OverlayItem(new Ponto(-10.7383145,-37.49291661666660),"RJ"," "));
flag = true;
*/	
		newInstanceItemOverlay();
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			if(TODOS_PONTOS){
				chamaWBS.setMetodo(MetodosWBS.TODOS_PONTOS);
			}
			else{
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
			}
		
//			chamaWBS.setMetodo("resposta");	
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo.equals("ERRO")){
				return false;
			}
			if(spo!=null){
				String retorno = spo.toString();
				String[] listaPercursos = retorno.toString().split(",");
				StringBuilder percursos = new StringBuilder();
				for (int i = 0; i < listaPercursos.length; i++) {
					if(!listaPercursos[i].equals(":")){
						percursos.append(listaPercursos[i]+",");
					}else{
						String[] localList = percursos.toString().split(",");
						double latitudep = Double.valueOf(localList[0]);
						double longitudep = Double.valueOf(localList[1]);
						String descricao = localList[2];
						Ponto ponto = new Ponto(latitudep,longitudep);
						this.itens.add(new OverlayItem(ponto,descricao," "));
						percursos = new StringBuilder();
					}
				}
				if(item != null){
					this.itens.add(item);
				}
				populate();
				flag = true;
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return flag;
	}
	
	public boolean alterarRaio(String r){
		newInstanceItemOverlay();
		this.raio = Integer.valueOf(r);
		return true;
	}
	
	public boolean carregaItensAoRedorMapa(Location location) {
			TODOS_PONTOS = false;
			return carregaItensMapa(location,null);
	}

	@Override protected boolean onTap(int index){ 
		Toast.makeText(contexto,
					   getItem(index).getTitle(), 
					   Toast.LENGTH_LONG).show(); 
		return true; 
	}

	@Override
	public int size() {
		return this.itens.size();
	}

	@Override
	protected OverlayItem createItem(int index) {
		return this.itens.get(index);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
            start = e.getEventTime();
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            stop = e.getEventTime();
        }
        if (stop - start > 1000) {
        	mapa.cadastraMarcacao();
        	final MapView mapV = mapView;
        	final MotionEvent event = e;
                	 	point = mapV.getProjection()
                	 							.fromPixels(
                	 									(int) event.getX(),
                	 									(int) event.getY()
                	 							);
        }
		return false;
	}
	
	public boolean cadastrarPonto(String descricao){
		boolean teveSucesso = false;
		if(descricao != null && descricao.length() > 0){
//			int duracao = 50001;
//			String msg = "<Cadastrado> - Sua Localização: "+
//	 				   point.getLatitudeE6()  / 1E6 +","+
//	 				   point.getLongitudeE6() / 1E6+": "+descricao;
//			Toast toast=Toast.makeText(contexto,msg,duracao);
//			toast.show();
			try{
				MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
				chamaWBS.setMetodo(MetodosWBS.GRAVAR_MARCACAO_GPS);

				chamaWBS.addParametro("4");
				chamaWBS.addParametro(String.valueOf((point.getLatitudeE6()  / 1E6)));
				chamaWBS.addParametro(String.valueOf((point.getLongitudeE6() / 1E6)));
				chamaWBS.addParametro(descricao);
				Object  spo = (Object) chamaWBS.iniciarWBS();
				if(spo.equals("ERRO")){
					return false;
				}
				if(spo!=null){
					Ponto ponto = new Ponto(point.getLatitudeE6()/1E6,point.getLongitudeE6()/1E6);
//					this.itens.add(new OverlayItem(ponto,descricao," "));
					carregaTodosItensMapa(null,new OverlayItem(ponto,descricao," ") );
//					populate();
					teveSucesso = true;
				}
			 }catch(Exception e){
				 System.out.println(e.getMessage());
			 }
		}
		return teveSucesso;
	}
	
	public static ItemOverlay getOverlay() {
		return overlay;
	}

	public static Location getLocation() {
		return location;
	}

	public static void setLocation(Location location) {
		ItemOverlay.location = location;
	}
	
	public MyLocationOverlay getMyLocationOverlay() {
		return myLocationOverlay;
	}
	
	public MapView getMapView() {
		return mapView;
	}
	
	public boolean enableCompass(){
		return myLocationOverlay.enableCompass();
	}
	
	public boolean enableMyLocation(){
		return myLocationOverlay.enableMyLocation();
	}

	public void disableCompass(){
		myLocationOverlay.disableCompass();
	}
	
	public void disableMyLocation(){
		myLocationOverlay.disableMyLocation();
	}

}
