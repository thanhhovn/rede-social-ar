package br.com.realidadeAumentada.maps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class CustonOverlay extends Overlay{
    
   private Context contexto;
   private static int raio = 5;
   private int cor;
   private Paint paint = new Paint();
   private GeoPoint ponto;
	private long start;
	private long stop;
   
   public CustonOverlay(GeoPoint ponto,int cor){
	   this.ponto = ponto;
	   this.cor = cor;
   }
   
   public CustonOverlay(Context contexto){
	   this.contexto = contexto;
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
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
	   if (e.getAction() == MotionEvent.ACTION_DOWN) {
           start = e.getEventTime();
       }
       if (e.getAction() == MotionEvent.ACTION_UP) {
           stop = e.getEventTime();
       }
       if (stop - start > 1500) {// Tempo em segundo necessário para ativação
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
