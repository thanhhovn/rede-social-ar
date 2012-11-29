package br.com.realidadeAumentada;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{

	private Button btEntrar = null;
	private TextView nAcessarConta = null;
	private CheckBox lembrarUsuario = null;
	
	private EditText nomeUsuario = null;
	private EditText senhaUsuario = null;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.login);
		
		nomeUsuario = (EditText) findViewById(R.id.etUsuario);
		senhaUsuario = (EditText) findViewById(R.id.etSenha);
		
		btEntrar = (Button) findViewById(R.id.btEntrar);
		nAcessarConta = (TextView) findViewById(R.id.tv_acessarContaUsuario);
		lembrarUsuario = (CheckBox) findViewById(R.id.chkLembrar);
		
		lembrarUsuario.setOnClickListener(this);
		btEntrar.setOnClickListener(this);
		nAcessarConta.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		if(v == btEntrar){
			// TODO: Validar Usuário
			Intent intent = new Intent("USUARIO");
			intent.addCategory("CADASTRO");
			startActivity(intent);
		}
		if(v == nAcessarConta){
		}
		if(v == lembrarUsuario){
		}
	}
}
