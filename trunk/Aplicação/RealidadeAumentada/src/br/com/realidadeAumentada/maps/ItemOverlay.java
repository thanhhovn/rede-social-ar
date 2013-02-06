package br.com.realidadeAumentada.maps;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends ItemizedOverlay<OverlayItem>{

	private List<Ponto> listPontos;
	
	public ItemOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		// TODO Auto-generated constructor stub
	}
	
	public void addPontos(ArrayList<Ponto> ponto){
		listPontos = ponto;
		populate();
	}

	@Override
	public int size() {
		return listPontos.size();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return new OverlayItem(listPontos.get(i),null,null);
	}

}
