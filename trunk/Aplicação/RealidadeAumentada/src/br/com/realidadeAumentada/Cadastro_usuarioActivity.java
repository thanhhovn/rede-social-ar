package br.com.realidadeAumentada;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.R;
import br.com.realidadeAumentada.validador.ValidadorEmail;



public class Cadastro_usuarioActivity extends Activity implements OnClickListener, OnFocusChangeListener {
		
	private Button cancelar = null;
	private Button confirmar = null;
	
	private EditText nome = null;
	private EditText email = null;
	private EditText senha = null;
	private EditText confirmaSenha = null;
	private TextView mensagemSenha = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);
		
		nome = (EditText) findViewById(R.id.et_nomeLoginUsuario);
		email = (EditText) findViewById(R.id.ed_emailUsuario);
		senha = (EditText) findViewById(R.id.et_senhaUsuario);
		mensagemSenha = (TextView) findViewById(R.id.tv_senhaNaoConferi);
		confirmaSenha = (EditText) findViewById(R.id.et_confirmarSenhaUsuario);
		cancelar = (Button) findViewById(R.id.btCancelar);
		confirmar = (Button) findViewById(R.id.btEntrar);
		
		 
		email.setOnFocusChangeListener(this);
		senha.setOnFocusChangeListener(this);
		confirmaSenha.setOnFocusChangeListener(this);
		
		confirmar.setOnClickListener(this);
		cancelar.setOnClickListener(this);
		
	}
	
	public void onClick(View v) {
		if(v == confirmar){
			if(senha.equals(confirmaSenha)){
			
			}else if(senha.getText().length() == 0 || confirmaSenha.getText().length() == 0){
				mensagemSenha.setText("Preencha a Senha.");
			}else{
				mensagemSenha.setText("Senha diferente!");
			}
			Intent intent = new Intent("USUARIO");
			intent.addCategory("PERFIL");
			startActivity(intent);
			
//			finishFromChild(getParent());
		}
		if(v == cancelar){
			Cadastro_usuarioActivity.this.finish();
		}
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if(v == email){
			TextView erro = (TextView) findViewById(R.id.tv_emailInvalido);
//			Context context=getApplicationContext();
//			String msg="Email Inválido!";
//			int duration=Toast.LENGTH_LONG;
//			Toast toast=Toast.makeText(context,msg,duration);
//			toast.setGravity(Gravity.TOP,0,0);
			
		       if (!hasFocus) {
		    	   if(email.getText().toString().length() > 0){
			    	   boolean isEmailValido = ValidadorEmail.validarEmail(email.getText().toString());
			    	   if(!isEmailValido){
//			    		   toast.setGravity(email.getGravity(), email.getScrollX(),email.getScrollY());
//			     	   	   toast.show();
			    		   erro.setText("Email Inválido.");
		    	   }
		       }else
		    	   erro.setText("");
		       
		       }
		if(v == senha || v == confirmaSenha)
			mensagemSenha.setText("");
		}
	}
}
