package br.com.realidadeAumentada;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import br.com.realidadeAumentada.GPS.LocationManagerHelper;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.util.Endereco;

public class PrincipalActivity extends Activity implements OnClickListener {

 private static final int CONTATO_DIALOG_ID = 1;
 
 protected LocationManager locationManager;
 
 private String descricaoMarcacao;
 private String mLatitude;
 private String mLongitude;
 private String tempo;
 private boolean OK = false;
 private TextView ultimoAcesso;
 private TextView usuarioLogado;
 private ImageView adicionaAmigo;
 private EditText mensagem;
 private Button editarPerfil;
 private Button visualizarMapa;
 private Button realidadeAumentada;
 private Button caixaMensagem;
 private Button enviarMensagem;
 private Button localizarUsuario;
 private Spinner listaAmigos;
 private Spinner listaUsuarios;
 private Button sair;
 private Integer amigoSelecionado;

 @Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.principal_l);

  realidadeAumentada = (Button) findViewById(R.id.bt_realidadeAumentada);
  visualizarMapa = (Button) findViewById(R.id.bt_visualizarMapa);
  editarPerfil = (Button) findViewById(R.id.bt_editarPerfilUsuario);

  ultimoAcesso = (TextView) findViewById(R.id.ultimoAcesso);
  usuarioLogado = (TextView) findViewById(R.id.usuarioLogado);
  adicionaAmigo = (ImageView)findViewById(R.id.adicionaAmigo);
  mensagem = (EditText) findViewById(R.id.mensagem);
  caixaMensagem = (Button) findViewById(R.id.bt_caixaMensagem);
  enviarMensagem = (Button) findViewById(R.id.bt_enviarMensagem);
  localizarUsuario = (Button) findViewById(R.id.bt_localizarUsuario);
  visualizarMapa = (Button) findViewById(R.id.bt_visualizarMapa);
  listaAmigos = (Spinner) findViewById(R.id.listaAmigos);
  sair = (Button) findViewById(R.id.bt_sair);
  
  realidadeAumentada.setOnClickListener(this);
  visualizarMapa.setOnClickListener(this);
  editarPerfil.setOnClickListener(this);
  caixaMensagem.setOnClickListener(this);
  enviarMensagem.setOnClickListener(this);
  localizarUsuario.setOnClickListener(this);
  visualizarMapa.setOnClickListener(this);
  sair.setOnClickListener(this);
  adicionaAmigo.setOnClickListener(this);
  ultimoAcesso.setText(Usuario.dadosLogin.getUltimoAcesso());
  usuarioLogado.setText(Usuario.usuarioLogado());
  carregaListaAmigos(false,null);
  listaAmigos.setOnItemSelectedListener(new OnItemSelectedListener() {
        public void onNothingSelected(AdapterView<?> arg0) {  }
        public void onItemSelected(AdapterView<?> parent, View v,
          int position, long id) {
        	if(position != 0){
        		Long usuario = parent.getSelectedItemId();
        		setAmigoSelecionado(usuario.intValue());
      	  	}else{
      	  		setAmigoSelecionado(0);
      	  	}
        }
	});
 }

public void onClick(View v) {
	if(v == editarPerfil){
		Intent intent = new Intent("USUARIO");
		intent.addCategory("PERFIL");
		startActivity(intent);
	}else
	if(v == visualizarMapa){
		LocationManagerHelper.setContext(this);
		if(!LocationManagerHelper.isAtivoGPS()){
			LocationManagerHelper.showSettingsAlert();
		}else{
			Intent intent = new Intent("TESTE");
			intent.addCategory("MAPA");
			startActivity(intent);
		}
	}else
	if(v == realidadeAumentada){
		LocationManagerHelper.setContext(this);
		if(!LocationManagerHelper.isAtivoGPS()){
			LocationManagerHelper.showSettingsAlert();
		}else{
			Intent intent = new Intent("TESTE");
			intent.addCategory("REALIDADE_AUMENTADA");
			startActivity(intent);
		}
	}else
	if(v == caixaMensagem){
		
	}else
	if(v == enviarMensagem){
		Integer posicao = getAmigoSelecionado();
		if(mensagem.getText().toString() != null && mensagem.getText().toString().length() > 0){
			if(posicao != 0){
				boolean retorno = Usuario.enviarMensagem(posicao,mensagem.getText().toString());
				if(retorno){
					String msg="Mensagem enviada com sucesso.";
					Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
					toast.show();
					limparCampos();
				}else{
					
				}
			}else{
				
			}
		}
	}else 
	if(v == localizarUsuario){
		
	}else if(v == adicionaAmigo){
		showDialog(CONTATO_DIALOG_ID);
//					dialog.setPositiveButton("OK", new 
//								DialogInterface.OnClickListener() {
//									@SuppressLint("DefaultLocale")
//									public void onClick(DialogInterface dialog,int which) {
//										
//										
//									Toast.makeText(PrincipalActivity.this.getBaseContext(),"Exibindo pontos de interesse com aproximação de "+""+" Metros",Toast.LENGTH_LONG).show();
//										
//										PrincipalActivity.this.removeDialog(NOME_DIALOG_ID);
//									}
//								}
//						);
//					dialog.setNegativeButton("Cancelar", new 
//								DialogInterface.OnClickListener() {
//									@SuppressLint("DefaultLocale")
//									public void onClick(DialogInterface dialog,int which) {
//										PrincipalActivity.this.removeDialog(NOME_DIALOG_ID);
//									}
//								}
//					);
	}else
	if(v == sair){
		this.finish();
	} 
	
}

private void limparCampos(){
	mensagem.setText("");
	carregaListaAmigos(false, null);
}

protected void showCurrentLocation() {
	//(int)(location.getLatitude()*1E6)))
	new LocationManagerHelper(this);
   
    mLatitude = String.valueOf(LocationManagerHelper.getLatitude());
	mLongitude =String.valueOf(LocationManagerHelper.getLongitude());
	tempo = String.valueOf(LocationManagerHelper.getCurrentTimeStamp());
}

private void carregaListaAmigos(boolean flag,Long idAmigo){
	List<String> amigos = new ArrayList<String>();
	if(flag){
		if(idAmigo != null){
			listaAmigos.setEnabled(flag);
			listaAmigos.setClickable(flag);
			listaAmigos.setAdapter(adapter(amigos));
		}
	}else{
		listaAmigos.setEnabled(true);
		listaAmigos.setClickable(true);
		String id = Usuario.getUsuarioLogado_id();
		amigos = Usuario.getAmigos(id);
		listaAmigos.setAdapter(adapter(amigos));
	}
}

private ArrayAdapter<String> adapter(List<String> dado){
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dado);
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	return adapter; 
}

@Override
	protected Dialog onCreateDialog(int id) {
	switch (id) {
		case (CONTATO_DIALOG_ID) :{
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layoutContato = inflater.inflate(R.layout.adicionar_contato,(ViewGroup) findViewById(R.id.layoutAdicionarContato));
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(PrincipalActivity.this);
			dialog.setTitle("Adicionando Amigo");
			dialog.setView(layoutContato);
			
			final TextView ultimoAcesso = (TextView) layoutContato.findViewById(R.id.ultimoAcesso);
			final TextView usuarioLogado = (TextView) layoutContato.findViewById(R.id.nome_usuarioLogado);
			final Button adicionarUsuario = (Button) layoutContato.findViewById(R.id.btAdicionarUsuario);
			final Button voltar = (Button) layoutContato.findViewById(R.id.btVoltar);
			ultimoAcesso.setText(Usuario.dadosLogin.getUltimoAcesso());
			usuarioLogado.setText(Usuario.usuarioLogado());
			listaUsuarios = (Spinner) layoutContato.findViewById(R.id.usuarios);
			listaUsuarios.setEnabled(true);
			listaUsuarios.setClickable(true);
			carregaListaUsuarios();
			listaUsuarios.setOnItemSelectedListener(new OnItemSelectedListener() {
		        public void onNothingSelected(AdapterView<?> arg0) {  }
		        public void onItemSelected(AdapterView<?> parent, View v,
		          int position, long id) {
		        	if(position != 0){
		        		Long usuario = parent.getSelectedItemId();
		        		setAmigoSelecionado(usuario.intValue());
		      	  	}else{
		      	  		setAmigoSelecionado(0);
		      	  	}
		        }
			});
			
			adicionarUsuario.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					if(getAmigoSelecionado() != 0){
						boolean resultado = Usuario.addContato(Usuario.getUsuarioLogado_id(),getAmigoSelecionado());
						if(resultado){
							Toast.makeText(PrincipalActivity.this.getBaseContext(),"Contato adicionado com sucesso!",Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(PrincipalActivity.this.getBaseContext(),"Não foi possível adicionar o usuário selecionado a lista de contatos.",Toast.LENGTH_LONG).show();
						}
						setAmigoSelecionado(0);
						carregaListaUsuarios();
					}
				}
			});
			voltar.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					carregaListaAmigos(false,null);
					PrincipalActivity.this.removeDialog(CONTATO_DIALOG_ID);
				}
			});
//			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			final View layout = inflater.inflate(R.layout.alerta_nome,(ViewGroup) findViewById(R.id.layoutAlertaNome));
//			
//			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//			dialog.setTitle("Nome");
//			dialog.setView(layout);
//
//			final EditText editNome = (EditText) layout.findViewById(R.id.et_AlertaNomeUsuario);
//
//			dialog.setPositiveButton("OK", new 
//						DialogInterface.OnClickListener() {
//							@SuppressLint("DefaultLocale")
//							public void onClick(DialogInterface dialog,int which) {
//								String nome	= editNome.getText().toString();
//								if(nome != null && nome.length() > 0){
//									descricaoMarcacao = nome.toUpperCase();
//								    OK = true;							
//								}
//								PrincipalActivity.this.removeDialog(CONTATO_DIALOG_ID);
//							}
//						}
//				);
//			dialog.setNegativeButton("Cancelar", new 
//					DialogInterface.OnClickListener() {
//						@SuppressLint("DefaultLocale")
//						public void onClick(DialogInterface dialog,int which) {
//							OK = false;
//							PrincipalActivity.this.removeDialog(CONTATO_DIALOG_ID);
//						}
//					}
//			);
			return dialog.create();
		}
	}
	return null;
	}

private void carregaListaUsuarios() {
	List<String> amigos = new ArrayList<String>();
	String idUsuario = Usuario.getUsuarioLogado_id();
	amigos = Usuario.getUsuarios(idUsuario);
	listaUsuarios.setAdapter(adapter(amigos));
}

public Integer getAmigoSelecionado() {
	return amigoSelecionado;
}

public void setAmigoSelecionado(Integer amigoSelecionado) {
	this.amigoSelecionado = amigoSelecionado;
}
 
}  