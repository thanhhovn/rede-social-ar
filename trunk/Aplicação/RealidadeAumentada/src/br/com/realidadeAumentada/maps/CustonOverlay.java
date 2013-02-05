package br.com.realidadeAumentada.maps;

import android.graphics.Canvas;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class CustonOverlay extends Overlay{

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		super.draw(canvas, mapView, shadow);
		// TODO Auto-generated method stub
		return true;
	}
}
