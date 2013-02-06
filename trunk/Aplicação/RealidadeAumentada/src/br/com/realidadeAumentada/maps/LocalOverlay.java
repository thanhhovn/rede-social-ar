package br.com.realidadeAumentada.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class LocalOverlay extends MyLocationOverlay {

	private Context contexto;
	private MapView mapView;

	
	public LocalOverlay(Context context,MapView mapView) {
		super(context, mapView);
		contexto = context;
		this.mapView = mapView;
	}
	
	public List<CustonOverlay> getListOverlay(){
		List<CustonOverlay> lista = new ArrayList<CustonOverlay>(0);
		
		lista.add(new CustonOverlay(new Ponto(-10.7483672,-37.49421661666667,"PE"),Color.CYAN));
		lista.add(new CustonOverlay(new Ponto(-10.7483373,-37.49291661666657,"SP"),Color.GREEN));
		lista.add(new CustonOverlay(new Ponto(-10.7383145,-37.49291661666660,"SP"),Color.RED));
	/*	
		List<CustonOverlay> lista = new ArrayList<CustonOverlay>(0);
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.EXIBIR_TODOS_PONTOS);
			
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
						Ponto ponto = new Ponto(latitude,longitude,descricao,Color.RED);
						lista.add(new CustonOverlay(ponto));
						percursos = new StringBuilder();
					}
				}
			}
		 }catch(Exception e){
			 System.out.println(e.getCause());
			 System.out.println(e.getMessage());
		 }
		*/
		return lista;
	}
	
	public static Ponto newPonto(){
		int latitude = (int) (-10.7483672 * 1000000);
		int longitude = (int) (-37.49421661666667 * 1000000); 
		return new Ponto(latitude,longitude,"Campo do Brito");
	}

	
	/**
	 * 
	 * @param resources
	 * @param canvas
	 * @param mapView
	 * @param shadow
	 * @param when
	 * @param imagem
	 * @return
	 */
/*	public boolean draw(Resources resources, Canvas canvas, MapView mapView,boolean shadow, long when,int imagem) {
		draw(canvas,mapView,shadow);
		Point pontosTela = new Point();
		mapView.getProjection().toPixels(ponto,pontosTela);
		Bitmap bmp = BitmapFactory.decodeResource(resources,imagem);
		canvas.drawBitmap(bmp,pontosTela.x,pontosTela.y-50,null);
		return true;
	}
*/	

}
