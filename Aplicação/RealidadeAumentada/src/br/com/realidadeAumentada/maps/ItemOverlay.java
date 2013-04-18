package br.com.realidadeAumentada.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.MotionEvent;
import android.widget.Toast;
import br.com.realidadeAumentada.MapaActivity;
import br.com.realidadeAumentada.R;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.util.BalloonItemizedOverlay;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends BalloonItemizedOverlay<OverlayItem>{

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
	private static Integer indix = 0;
	

	public ItemOverlay(Drawable defaultMarker, Context contexto, MapaActivity mapa,MapView map) {
		super(boundCenterBottom(defaultMarker),map); //Marcação Padrão
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

	public void addNewItem(Marcador marcacao,String fragmento){
		this.itens.add(new OverlayItem(marcacao,marcacao.getDescricao(),fragmento));
		populate();
	}
	
	public void removeItem(int index){
		this.itens.remove(index);
		populate();
	}
	
	public boolean carregaTodosItensMapa(GeoPoint local,OverlayItem item){
		TODOS_PONTOS = true;
//		if(getLocation() != location && location != null){
//			setLocation(location);
//		}
		return carregaItensMapa(local,item);
	}
	
	private boolean carregaItensMapa(GeoPoint minhaLocalizacao, OverlayItem item){
		boolean flag = false;
// Dados de Teste		
/*		itens.add(new OverlayItem(new Ponto(-10.7483672,-37.49421661666667),"PE"," "));
		itens.add(new OverlayItem(new Ponto(-10.7483373,-37.49291661666657),"SP"," "));
		itens.add(new OverlayItem(new Ponto(-10.7383145,-37.49291661666660),"RJ"," "));

*/	
		newInstanceItemOverlay();
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			if(TODOS_PONTOS){
				chamaWBS.setMetodo(MetodosWBS.TODOS_PONTOS);
			}
			else{
				chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);

				Double latitude = Double.valueOf(Double.valueOf(minhaLocalizacao.getLatitudeE6()) / 1000000);
	        	Double longitude = Double.valueOf(Double.valueOf(minhaLocalizacao.getLongitudeE6()) / 1000000);
				chamaWBS.addParametro(String.valueOf(latitude));
				chamaWBS.addParametro(String.valueOf(longitude));
				if(raio == null){
					chamaWBS.addParametro(String.valueOf(10000)); // 10 KM
				}else{
					chamaWBS.addParametro(String.valueOf(raio));
				}
				chamaWBS.addParametro(Usuario.getUsuarioLogado_id());
			}
		
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo.equals("ERRO")){
				return false;
			}
			if(spo!=null){
				Drawable imagemMarcador = null;
				OverlayItem overlayItem = null;
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
						String titulo = localList[3];
						String idUsuario = localList[4];
						String idMarcador = localList[5];
						Marcador marcacao = new Marcador(latitudep,longitudep,titulo,descricao,idUsuario,idMarcador);
						
						if(Usuario.getUsuarioLogado_id() != null && Usuario.getUsuarioLogado_id().equalsIgnoreCase(idUsuario)){
							imagemMarcador = contexto.getResources().getDrawable(R.drawable.meu_marcador);
							overlayItem = new OverlayItem(marcacao,titulo,descricao);
							mapMarcadores.put(getIndix(), marcacao);
							atualizaIndix();
							Usuario.addMarcador(marcacao);
						}else{
							imagemMarcador = contexto.getResources().getDrawable(R.drawable.marcador);
							overlayItem = new OverlayItem(marcacao,titulo,descricao);
						}
						imagemMarcador.setBounds(0,0, imagemMarcador.getIntrinsicWidth(),imagemMarcador.getIntrinsicHeight());
						overlayItem.setMarker(imagemMarcador);
						this.itens.add(overlayItem);
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
	
	public boolean carregaItensAoRedorMapa(GeoPoint location) {
			TODOS_PONTOS = false;
			return carregaItensMapa(location,null);
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		Toast.makeText(contexto, "onBalloonTap for overlay index " + index,
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
	
	
	public boolean cadastrarPonto(String titulo,String descricao,Location location){
		boolean teveSucesso = false;
		if(descricao != null && descricao.length() > 0){
			try{
				MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
				chamaWBS.setMetodo(MetodosWBS.GRAVAR_MARCACAO_GPS);
				StringBuilder dadosMarcacao = new StringBuilder();
				dadosMarcacao.append(String.valueOf(Usuario.getUsuarioLogado_id())+",");
				if(location == null){
					dadosMarcacao.append(String.valueOf((point.getLatitudeE6()  / 1E6))+",");
					dadosMarcacao.append((point.getLongitudeE6() / 1E6)+",");
				}else{
					dadosMarcacao.append(String.valueOf(location.getLatitude())+",");
					dadosMarcacao.append(String.valueOf(location.getLongitude())+",");
				}
				dadosMarcacao.append(titulo+",");
				dadosMarcacao.append(descricao);
				chamaWBS.addParametro(dadosMarcacao.toString());
				Object  spo = (Object) chamaWBS.iniciarWBS();
				if(spo.equals("ERRO")){
					return false;
				}
				if(spo!=null){
					String idMarcador = spo.toString();
					String usuario = Usuario.getUsuarioLogado_id();
					Marcador marcacao = new Marcador(point.getLatitudeE6()/1E6,point.getLongitudeE6()/1E6,titulo,descricao,usuario,idMarcador);
					mapMarcadores.put(getIndix(), marcacao);
					atualizaIndix();
					Usuario.addMarcador(marcacao);
					Drawable imagemMarcador = contexto.getResources().getDrawable(R.drawable.meu_marcador);
					OverlayItem overlayItem = new OverlayItem(marcacao,titulo,descricao);
					imagemMarcador.setBounds(0,0, imagemMarcador.getIntrinsicWidth(),imagemMarcador.getIntrinsicHeight());
					overlayItem.setMarker(imagemMarcador);
					this.itens.add(overlayItem);
					populate();
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

	public void addMapMarcador(Marcador marcador, Integer indix) {
		this.mapMarcadores.put(indix, marcador);
	}

	public static Integer getIndix() {
		return indix;
	}

	public static void atualizaIndix() {
		indix++;
	}

}
