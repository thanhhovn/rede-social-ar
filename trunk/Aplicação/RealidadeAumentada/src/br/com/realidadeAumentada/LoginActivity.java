package br.com.realidadeAumentada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	private Button btEntrar = null;
	private Button btInscrever = null;
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
		btInscrever = (Button) findViewById(R.id.btInscrever);
		nAcessarConta = (TextView) findViewById(R.id.tv_acessarContaUsuario);
		lembrarUsuario = (CheckBox) findViewById(R.id.chkLembrar);
		
		lembrarUsuario.setOnClickListener(this);
		btEntrar.setOnClickListener(this);
		btInscrever.setOnClickListener(this);
		nAcessarConta.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		Context context=getApplicationContext();
		int duration=Toast.LENGTH_SHORT;
		if(v == btEntrar){
			String usuario = "lucas";
			String senha = "123";
			if(nomeUsuario != null && senhaUsuario != null 
			   && nomeUsuario.getText().toString().equals(usuario) 
			   && senhaUsuario.getText().toString().equals(senha))
			{
				Intent intent = new Intent("TELAPRINCIPAL");
				intent.addCategory("APLICACAO");
				startActivity(intent);
			}else{
				String msg="Senha ou Usuário Inválido.";
				Toast toast=Toast.makeText(context,msg,duration);
				toast.show();
			}
		}
		
		if(v == btInscrever){
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
