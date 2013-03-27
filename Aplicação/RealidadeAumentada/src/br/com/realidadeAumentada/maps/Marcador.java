package br.com.realidadeAumentada.maps;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class Marcador extends GeoPoint{

	private String idMarcador;
	private String titulo;
	private String descricao;
	private Double latitude;
	private Double longitue;
	private String idUsario;

	public Marcador(Location localizacao,String descricao,String usuario,String idMarcador){
		this(localizacao.getLatitude(),localizacao.getLongitude(),descricao,usuario,idMarcador);
	}
		
	private Marcador(int latitudeE6, int longitudeE6,String descricao) {
		super(latitudeE6, longitudeE6);
		this.descricao = descricao;

	}
	
	public Marcador(double latitude,double longitude,String descricao,String usuario,String idMarcador){
		this((int)(latitude*1E6),(int)(longitude*1E6),descricao);
		setLatitude(latitude);
		setLongitue(longitude);
		setDescricao(descricao);
		setIdUsario(usuario);
		setIdMarcador(idMarcador);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitue() {
		return longitue;
	}

	public void setLongitue(Double longitue) {
		this.longitue = longitue;
	}
	
	public String getIdUsario() {
		return idUsario;
	}

	public void setIdUsario(String idUsario) {
		this.idUsario = idUsario;
	}

	public String getIdMarcador() {
		return idMarcador;
	}

	public void setIdMarcador(String idMarcador) {
		this.idMarcador = idMarcador;
	}


}
