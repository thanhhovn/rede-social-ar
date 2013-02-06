package br.com.realidadeAumentada.maps;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class Ponto extends GeoPoint{

	private String descricao;

	public Ponto(Location localizacao,String descricao){
		this(localizacao.getLatitude(),localizacao.getLongitude(),descricao);
	}
	
	public Ponto(double latitude,double longitude,String descricao){
		this((int)(latitude*1E6),(int)(longitude*1E6),descricao);
	}
	
	private Ponto(int latitudeE6, int longitudeE6,String descricao) {
		super(latitudeE6, longitudeE6);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
