package br.com.realidadeAumentada;

import com.google.android.maps.OverlayItem;

import br.com.realidadeAumentada.cadastroUsuario.DadosPerfil;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.maps.Ponto;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
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
	
	private EditText emailUsuario = null;
	private EditText senhaUsuario = null;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.login);
		
		emailUsuario = (EditText) findViewById(R.id.etUsuario);
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
			if(emailUsuario != null && senhaUsuario != null)
			{
				try{
					MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
					chamaWBS.setMetodo(MetodosWBS.LOGIN);
					
					chamaWBS.addParametro(emailUsuario.getText().toString());
					chamaWBS.addParametro(senhaUsuario.getText().toString());
					Object  spo = (Object) chamaWBS.iniciarWBS();
					if(spo!=null && !spo.toString().equals("ERRO")){
						String retorno = spo.toString();
						String[] dadosUsuario = retorno.toString().split(",");
						
						Usuario.setUsuario_id(dadosUsuario[0]);
						Usuario.dadosLogin.setNome_login(dadosUsuario[1]);
						Usuario.dadosLogin.setDataLogin(dadosUsuario[2]);
						
						
						Usuario.dadosPerfil.setNome(dadosUsuario[3]);
						Usuario.dadosPerfil.setSexo(dadosUsuario[4]);
						Usuario.dadosPerfil.setDt_nascimento(dadosUsuario[5]);
						Usuario.dadosPerfil.setStatus_relacionamento(dadosUsuario[6]);
						Usuario.dadosPerfil.setNivel_escolar(dadosUsuario[7]);
						
						if(!dadosUsuario[8].toString().equals("vazio")){
							Usuario.dadosPerfil.setProfissao(dadosUsuario[8]);
						}
						if(!dadosUsuario[9].toString().equals("vazio")){
							Usuario.dadosPerfil.setTelefone(dadosUsuario[9]);
						}
						Usuario.dadosPerfil.getEndereco().setEnderecoId(dadosUsuario[10]);
						Usuario.dadosPerfil.getEndereco().setNome_pais(dadosUsuario[11]);
						Usuario.dadosPerfil.getEndereco().setSigla_estado(dadosUsuario[12]);
						Usuario.dadosPerfil.getEndereco().setCod_cidade(dadosUsuario[13]);
						Usuario.dadosPerfil.getEndereco().setNome_cidade(dadosUsuario[14]);
						Usuario.dadosPerfil.getEndereco().setNome_bairro(dadosUsuario[15]);
						Usuario.dadosPerfil.getEndereco().setNome_logradouro(dadosUsuario[16]);
						Usuario.dadosPerfil.getEndereco().setNumero_endereco(dadosUsuario[17]);
						Usuario.dadosPerfil.getEndereco().setNome_Estado(dadosUsuario[18]);
						
						Intent intent = new Intent("TELAPRINCIPAL");
						intent.addCategory("APLICACAO");
						startActivity(intent);
					}else{
						String msg="Senha ou Usuário Inválido.";
						Toast toast=Toast.makeText(context,msg,duration);
						toast.show();
					}
				 }catch(Exception e){
					 System.out.println(e.getMessage());
				 }
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
