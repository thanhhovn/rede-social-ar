package br.com.realidadeAumentada.maps;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.Toast;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class LocalOverlay extends MyLocationOverlay {
	private int cor;
	private Context contexto;
	Ponto ponto = null;
	
	public LocalOverlay(Context context, MapView mapView) {
		super(context, mapView);
		contexto = context;
	}
	
	public LocalOverlay(Context context, MapView mapView,Ponto ponto) {
		super(context, mapView);
		contexto = context;
		this.ponto = ponto;
	}
	
	public LocalOverlay(Context context, MapView mapView,Ponto ponto,int cor) {
		super(context, mapView);
		contexto = context;
		setCor(cor);
		this.ponto = ponto;
	}
	
	public List<LocalOverlay> getListOverlay(){
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.EXIBIR_TODOS_PONTOS);
			//chamaWBS.addParametro("e");
			Object  spo = (Object) chamaWBS.iniciarWBS();

			if(spo!=null){
				// TODO : Montar a lista de Pontos
			}
		 }catch(Exception e){
			 System.out.println(e.getCause());
			 System.out.println(e.getMessage());
		 }
		return null;
	}
	
	public int getCor() {
		return cor;
	}
	
	public void setCor(int cor) {
		this.cor = cor;
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
	public boolean draw(Resources resources, Canvas canvas, MapView mapView,boolean shadow, long when,int imagem) {
		draw(canvas,mapView,shadow);
		Point pontosTela = new Point();
		mapView.getProjection().toPixels(ponto,pontosTela);
		Bitmap bmp = BitmapFactory.decodeResource(resources,imagem);
		canvas.drawBitmap(bmp,pontosTela.x,pontosTela.y-50,null);
		return true;
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas,mapView,shadow);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		if(event.getAction() == 1){
			GeoPoint point = mapView.getProjection()
					     			.fromPixels(
												(int) event.getX(),
												(int) event.getY()
									);
			Toast.makeText(contexto,
					       "Location: "+
					        point.getLatitudeE6()  / 1E6 +","+
						    point.getLongitudeE6() / 1E6,
						    Toast.LENGTH_SHORT).show();											 
		}
		return false;
	}

}
