package br.com.realidadeAumentada.cadastroUsuario;

import android.os.Handler;



public class Treath implements Runnable{

	private int duracao;
	
	public Treath(int duracao){
		this.duracao = duracao;
		Handler h = new Handler();
		h.postDelayed(this, duracao);
	}
	
	public void run() {
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getDuracao() {
		return duracao;
	}
	

}