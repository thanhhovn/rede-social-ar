package br.com.realidadeAumentada;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Cadastro_usuarioActivity extends Activity{

	private static final int CODIGO = 1;
	private static final int TIME_DIALOG = 1;
	private static final int Nome_DIALOG = 5;
	//Esta causando erro na aplicação ao clicar no botão Entrar da tela de login
//	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);
		LinearLayout nomeUsuario	= (LinearLayout) findViewById(R.id.layoutNomeUsuario);
		LinearLayout tipo	= (LinearLayout) findViewById(R.id.layoutRelacionamentoUsuario);
		
		nomeUsuario.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				showDialog(Nome_DIALOG);
				/*Código para AlertDialog
				AlertDialog.Builder nome = new AlertDialog.Builder(Cadastro_usuarioActivity.this);
				nome.setTitle("Nome");
//				nome.setView(alertaNome);
				nome.setPositiveButton("OK",new 
						Dialog.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								TextView nome	= (TextView) findViewById(R.id.tv_respostaUsuario);
							}
						}	
				);
				nome.show();
				*/
			}
		});
		
		tipo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent("RELACIONAMENTO");
				it.addCategory("LISTA");
				startActivityForResult(it,CODIGO);
			}
		});
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case (TIME_DIALOG):
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Date dataAtual = new Date(java.lang.System.currentTimeMillis());
				String dataFormatada = sdf.format(dataAtual);
				AlertDialog time = (AlertDialog) dialog;
				time.setMessage(dataFormatada);
			break;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case (TIME_DIALOG): 
			AlertDialog.Builder time = new AlertDialog.Builder(this);
			time.setTitle("Data Atual");
			time.setMessage("Agora");
			return time.create();
		case (Nome_DIALOG) :
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.alerta_nome,(ViewGroup) findViewById(R.id.layoutAlertaNome));
			
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setTitle("Nome");
			dialog.setView(layout);
			
			final EditText editNome = (EditText) layout.findViewById(R.id.et_AlertaNomeUsuario);
			
			dialog.setButton("OK", new 
						DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								
								String nome	= editNome.getText().toString();
								if(nome != null && nome.length() > 0){
									TextView resposta = (TextView) findViewById(R.id.tv_respostaNomeUsuario);
									resposta.setText(nome.toUpperCase());
								}
							}
						}
				);
			return dialog;
		}
		
		return null;
	}
	
	protected void onActivityResult(int codigo, int resultado, Intent it){
		if(codigo == CODIGO && it != null){
			Bundle parans = it.getExtras();
			if(parans != null){
				String relacionamento = parans.getString("relacionamento");
				TextView tipo	= (TextView) findViewById(R.id.tv_resposta_relacionamentoUsuario);
				tipo.setText(relacionamento);
			}
		}
	}

}
