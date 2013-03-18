package br.com.realidadeAumentada;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class ApresentacaoActivity extends Activity implements Runnable {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState); 
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.apresentacao);
			
			Handler h = new Handler();
			h.postDelayed(this, 2000);
		}
		
		public void run(){
//			Intent it = new Intent("TELAPRINCIPAL");
//			it.addCategory("APLICACAO");
			
			Intent it = new Intent("TELA_LOGIN");
			it.addCategory("LOGIN");
//			Intent it = new Intent("TESTE");
//			it.addCategory("MAPA");
//			it.addCategory("REALIDADE_AUMENTADA");
//			Intent it = new Intent("USUARIO");
//			it.addCategory("PERFIL");
			startActivity(it);
			finish();
		}
	}