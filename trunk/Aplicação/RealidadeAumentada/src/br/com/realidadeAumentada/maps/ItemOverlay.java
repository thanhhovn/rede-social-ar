package br.com.realidadeAumentada.maps;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.MotionEvent;
import android.widget.Toast;
import br.com.realidadeAumentada.TesteMapa;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> itens;
	private Context contexto;
	TesteMapa mapa;
	private GeoPoint point;
	private long start;
	private long stop;
	private static Integer raio= 300;
	private static ItemOverlay overlay;
	
	public ItemOverlay(Drawable defaultMarker, Context contexto, TesteMapa mapa) {
		super(boundCenterBottom(defaultMarker)); //Marca��o Padr�o
		this.contexto = contexto;
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
	
	public boolean carregaItensMapa(Location location,Integer latitude,Integer longitude){
		boolean flag = false;
		// OverlayItem().setMarker(Drawable); permite que cada item possua uma imagem diferente.
// Dados de Teste		
/*		itens.add(new OverlayItem(new Ponto(-10.7483672,-37.49421661666667),"PE"," "));
		itens.add(new OverlayItem(new Ponto(-10.7483373,-37.49291661666657),"SP"," "));
		itens.add(new OverlayItem(new Ponto(-10.7383145,-37.49291661666660),"RJ"," "));
flag = true;

        	
*/		String lat = "-10.7483672";
		String longi = "-37.49421661666667"; 
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);
//			if(location != null){
//				lat   = String.valueOf(Double.valueOf(latitude/1000000));
//				longi = String.valueOf(Double.valueOf(longitude/1000000));
//			}
			chamaWBS.addParametro(lat);
			chamaWBS.addParametro(longi);
			chamaWBS.addParametro(String.valueOf(raio));
			Object  spo = (Object) chamaWBS.iniciarWBS();
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
				populate();
				flag = true;
			}
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return flag;
	}
	
	// TODO ir� chamar o m�todo carregaItensMapa para retornar os pontos segundo a nova configura��o do Raio
	public boolean alterarRaio(String r){
		newInstanceItemOverlay();
		this.raio = Integer.valueOf(r);
		return carregaItensMapa(null,null,null);
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
        	mapa.exibirDescricaoMarcador(ItemOverlay.this);
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
//			String msg = "<Cadastrado> - Sua Localiza��o: "+
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
				if(spo!=null){
					Ponto ponto = new Ponto(point.getLatitudeE6()/1E6,point.getLongitudeE6()/1E6);
					this.itens.add(new OverlayItem(ponto,descricao," "));
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
}
