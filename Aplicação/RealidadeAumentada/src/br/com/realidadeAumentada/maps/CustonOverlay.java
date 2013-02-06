package br.com.realidadeAumentada.maps;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class CustonOverlay extends Overlay{
    
   private static int raio = 5;
   private int cor;
   private Paint paint = new Paint();
   private GeoPoint ponto;
   
   public CustonOverlay(GeoPoint ponto,int cor){
	   this.ponto = ponto;
	   this.cor = cor;
   }
   
   @Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		if(ponto != null){
			paint.setColor(cor);
			Point pontot = mapView.getProjection().toPixels(ponto, null);
			canvas.drawCircle(pontot.x, pontot.y, getRaio(), paint);
		}
	}
   
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
	/*	if(event.getAction() == 1){
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
	
	*/
	return false;
	}
   
   public void setPonto(GeoPoint ponto){
	   this.ponto = ponto;
   }

   private static int getRaio() {
	   return raio;
   }
	
   public static void setRaio(int raio) {
	   CustonOverlay.raio = raio;
   }
	
}
