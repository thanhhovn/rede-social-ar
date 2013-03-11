package br.com.realidadeAumentada.maps;

import android.content.Context;
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
	
}
