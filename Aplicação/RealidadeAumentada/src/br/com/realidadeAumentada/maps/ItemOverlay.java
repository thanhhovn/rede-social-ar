package br.com.realidadeAumentada.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.Toast;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> itens;
	private Context contexto;
	private long start;
	private long stop;
	
	public ItemOverlay(Drawable defaultMarker, Context contexto) {
		super(boundCenterBottom(defaultMarker)); //Marcação Padrão
		this.contexto = contexto;
		itens = new ArrayList<OverlayItem>(0);
	}
	
	public void addNewItem(Ponto ponto,String fragmento){
		itens.add(new OverlayItem(ponto,ponto.getDescricao(),fragmento));
		populate();
	}
	
	public void removeItem(int index){
		itens.remove(index);
		populate();
	}
	
	public boolean carregaItensMapa(){
		boolean flag = false;
		// OverlayItem().setMarker(Drawable); permite que cada item possua uma imagem diferente.
// Dados de Teste		
/*		itens.add(new OverlayItem(new Ponto(-10.7483672,-37.49421661666667),"PE"," "));
		itens.add(new OverlayItem(new Ponto(-10.7483373,-37.49291661666657),"SP"," "));
		itens.add(new OverlayItem(new Ponto(-10.7383145,-37.49291661666660),"RJ"," "));
*/		
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.PONTOS_AO_REDOR);
			chamaWBS.addParametro("-10.7483672");
			chamaWBS.addParametro("-37.49421661666667");
			chamaWBS.addParametro("150");
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
						double latitude = Double.valueOf(localList[0]);
						double longitude = Double.valueOf(localList[1]);
						String descricao = localList[2];
						Ponto ponto = new Ponto(latitude,longitude);
						itens.add(new OverlayItem(ponto,descricao," "));
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
	
	@Override protected boolean onTap(int index){ 
		Toast.makeText(contexto,
					   getItem(index).getTitle(), 
					   Toast.LENGTH_LONG).show(); 
		return true; 
	}

	@Override
	public int size() {
		return itens.size();
	}

	@Override
	protected OverlayItem createItem(int index) {
		return itens.get(index);
	}
	
/*	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {

		if (e.getAction() == MotionEvent.ACTION_DOWN) {
            start = e.getEventTime();
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            stop = e.getEventTime();
        }
        if (stop - start > 1500) {
        	GeoPoint point = mapView.getProjection()
				     			.fromPixels(
											(int) e.getX(),
											(int) e.getY()
								);
        	Toast.makeText(contexto,
				       "Location: "+
				        point.getLatitudeE6()  / 1E6 +","+
					    point.getLongitudeE6() / 1E6,
					    Toast.LENGTH_SHORT).show();
        }	
		return false;
	}
*/	

}
