package br.com.realidadeAumentada;

import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	private EditText emailUsuario = null;
	private EditText senhaUsuario = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.login);
		
		emailUsuario = (EditText) findViewById(R.id.etUsuario);
		senhaUsuario = (EditText) findViewById(R.id.etSenha);
		
		btEntrar = (Button) findViewById(R.id.btEntrar);
		btInscrever = (Button) findViewById(R.id.btInscrever);

		btEntrar.setOnClickListener(this);
		btInscrever.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		Context context=getApplicationContext();
		int duration=Toast.LENGTH_SHORT;
		if(v == btEntrar){
			if(emailUsuario != null && senhaUsuario != null)
			{
				if(MontandoChamadaWBS.isServidorDisponivel()){
					try{
						MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
						chamaWBS.setMetodo(MetodosWBS.LOGIN);
						
						chamaWBS.addParametro(emailUsuario.getText().toString());
						chamaWBS.addParametro(senhaUsuario.getText().toString());
						Object  spo = (Object) chamaWBS.iniciarWBS();
						if(spo!=null){
							String retorno = spo.toString();
							String[] dadosUsuario = retorno.toString().split(",");
							
							Usuario.setUsuario_id(dadosUsuario[0]);
							Usuario.setUsuarioLogado_id(dadosUsuario[0]);
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
							this.finish();
						}else{
							String msg="Usuário não cadastrado.";
							Toast toast=Toast.makeText(context,msg,duration);
							toast.show();
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}else{
						String msg="Não Foi Possível se Conectar com o Servidor.";
						Toast toast=Toast.makeText(context,msg,duration);
						toast.show();
					}
			}
		}
		
		if(v == btInscrever){
			if(MontandoChamadaWBS.isServidorDisponivel()){
				Intent intent = new Intent("USUARIO");
				intent.addCategory("CADASTRO");
				startActivity(intent);
			}else{
				String msg="Não Foi Possível se Conectar com o Servidor.";
				Toast toast=Toast.makeText(context,msg,duration);
				toast.show();
			}
		}
	}
}
