package br.com.realidadeAumentada;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.util.ValidadorEConversorUtil;


public class CadastroUsuarioActivity extends Activity implements Runnable, OnClickListener, OnFocusChangeListener, OnKeyListener {
		
	private Button cancelar = null;
	private Button confirmar = null;
	
	private EditText nome = null;
	private EditText email = null;
	private EditText senha = null;
	private EditText confirmaSenha = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);

		nome = (EditText) findViewById(R.id.et_nomeLoginUsuario);
		email = (EditText) findViewById(R.id.ed_emailUsuario);
		senha = (EditText) findViewById(R.id.et_senhaUsuario);
		confirmaSenha = (EditText) findViewById(R.id.et_confirmarSenhaUsuario);
		cancelar = (Button) findViewById(R.id.btCancelar);
		confirmar = (Button) findViewById(R.id.btEntrar);
		 
		email.setOnFocusChangeListener(this);
		senha.setOnFocusChangeListener(this);
		confirmaSenha.setOnFocusChangeListener(this);
		
		confirmar.setOnClickListener(this);
		cancelar.setOnClickListener(this);
		
		ocultarTeclado(nome);
	}
	
	private void limparCampos(){
		nome = null;
		email = null;
		senha = null;
	}
	
	private void ocultarTeclado(EditText edit){
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//edit.setFocusableInTouchMode(false); // Passa o foco para o próximo componente
	}
	
	public void onClick(View v) {
		if(v == confirmar){
			boolean flag = false;
			boolean exibirMensagem = false;
			Context context=getApplicationContext();
			int duration=Toast.LENGTH_LONG;
			String msg=null;
			if(camposEmpty()){
				msg = "Não Deixe Nenhum Campo Sem Preencher.";
				exibirMensagem = true;
			}else{
				if (isEmailValido(email)){
					if(senha.getText().toString().equals(confirmaSenha.getText().toString())){
						Usuario.newInstance();
						Usuario.dadosLogin.setNome_login(nome.getText().toString());
						Usuario.dadosLogin.setEmail(email.getText().toString());
						Usuario.dadosLogin.setSenha(senha.getText().toString());
						
						msg = "Você esta sendo redimencionado para a última tela de cadastro.";
						flag = true;
						exibirMensagem = true;
					}else{
						msg = "Senha e Contra Senha Estão Diferentes!";
						exibirMensagem = true;
					}
				}
			}
			if(exibirMensagem){
				Toast toast=Toast.makeText(context,msg,duration);
				toast.show();
			}
			if(flag){
				Handler h = new Handler();
				h.postDelayed(this, 2000);
			}
		}
		if(v == cancelar){
			CadastroUsuarioActivity.this.finish();
		}
	}
	
	private boolean camposEmpty(){
		return (nome.getText().length() == 0 || email.getText().length() == 0 
				|| senha.getText().length() == 0 || confirmaSenha.getText().length() == 0);
	}

	public void onFocusChange(View v, boolean hasFocus) {
		if(v == email){
			TextView erro = (TextView) findViewById(R.id.tv_emailInvalido);
		       if (!hasFocus) {
		    	   if(email.getText().toString().length() > 0){
			    	   if(!isEmailValido(email)){
			    		   erro.setText("Email Inválido.");
			    	   }else{
			    		   erro.setText("");
			    	   }
		       }else{
		    	   erro.setText("");
		       }
		    }
		}

	}
	
	private boolean isEmailValido(EditText email){
		return ValidadorEConversorUtil.validarEmail(email.getText().toString());
	}
	
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		return false;
	}

	public void run() {
		Intent intent = new Intent("USUARIO");
		intent.addCategory("PERFIL");
		startActivity(intent);
		finish();
	}
	
}
