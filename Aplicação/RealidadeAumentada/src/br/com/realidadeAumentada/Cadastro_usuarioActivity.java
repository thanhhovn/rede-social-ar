package br.com.realidadeAumentada;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.realidadeAumentada.validador.Mask;
import br.com.realidadeAumentada.validador.ValidadorEmail;



public class Cadastro_usuarioActivity extends Activity implements OnClickListener {

	private static final int LISTA_RELACIONAMENTO_ID = 0;
	private static final int LISTA_NIVEL_ESCOLARIDADE_ID = 1;
	private static final int TIME_DIALOG_ID = 2;
	private static final int DATE_DIALOG_ID = 3;
	private static final int NOME_DIALOG_ID = 4;
	
	private TextView respostaData = null;
	private EditText telefone = null;
	private LinearLayout nomeUsuario = null;
	private LinearLayout dataNascimento = null;
	private LinearLayout tipoRelacionamento = null;
	private LinearLayout escolaridade = null;
	//Variaveis para a Data
	private int ano;
    private int mes;
    private int dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_usuario);
		nomeUsuario	= (LinearLayout) findViewById(R.id.layoutNomeUsuario);
		tipoRelacionamento	= (LinearLayout) findViewById(R.id.layoutRelacionamentoUsuario);
		escolaridade	= (LinearLayout) findViewById(R.id.layoutEscolaridadeUsuario);
		dataNascimento = (LinearLayout) findViewById(R.id.layoutDataNascimentoUsuario);
		respostaData = (TextView) findViewById(R.id.tv_RespostadataNascimentoUsuario);
		telefone = (EditText) findViewById(R.id.ed_telefoneUsuario);
		telefone.addTextChangedListener(Mask.insert("(##)####-####", telefone));
		final EditText email = (EditText) findViewById(R.id.ed_emailUsuario);
		email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			   public void onFocusChange(View v, boolean hasfocus) {
			       if (!hasfocus) {
			    	   boolean isEmailValido = ValidadorEmail.validarEmail(email.getText().toString());
			    	   if(!isEmailValido){
			    		   TextView erro = (TextView) findViewById(R.id.tv_emailInvalido);
			    		   erro.setText("Email Inválido.");
			    	   }else{
			    		   
			    	   }
			       }
			   }
			}); 
		nomeUsuario.setOnClickListener(this);
		dataNascimento.setOnClickListener(this);
		tipoRelacionamento.setOnClickListener(this);
		escolaridade.setOnClickListener(this);
		carregaData();
	}
	
	private void carregaData(){
	      final Calendar c = Calendar.getInstance();
	        ano = c.get(Calendar.YEAR);
	        mes = c.get(Calendar.MONTH);
	        dia = c.get(Calendar.DAY_OF_MONTH);
	}
	
	// Atualiza a data no TextView
    private void updateDisplay() {
    	respostaData.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(dia).append("-")
                    .append(mes + 1).append("-")
                    .append(ano).append(" "));
    }
    
    // Seta o valor da data
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                    ano = year;
                    mes = monthOfYear;
                    dia = dayOfMonth;
                    updateDisplay();
                }
            };
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
			case (TIME_DIALOG_ID):{
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					Date dataAtual = new Date(java.lang.System.currentTimeMillis());
					String dataFormatada = sdf.format(dataAtual);
					AlertDialog time = (AlertDialog) dialog;
					time.setMessage(dataFormatada);
				break;
			}
			case (DATE_DIALOG_ID):{
			}
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
			case (TIME_DIALOG_ID):{ 
				AlertDialog.Builder time = new AlertDialog.Builder(this);
				time.setTitle("Data Atual");
				time.setMessage("Agora");
				return time.create();
			}
			case (NOME_DIALOG_ID) :{
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.alerta_nome,(ViewGroup) findViewById(R.id.layoutAlertaNome));
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("Nome");
				dialog.setView(layout);
				
				final EditText editNome = (EditText) layout.findViewById(R.id.et_AlertaNomeUsuario);
				
	//Mascara para CPF			
	//			editNome.addTextChangedListener(Mask.insert("###.###.###-##", editNome));
				dialog.setPositiveButton("OK", new 
							DialogInterface.OnClickListener() {
								@SuppressLint("DefaultLocale")
								public void onClick(DialogInterface dialog,int which) {
									
									String nome	= editNome.getText().toString();
									if(nome != null && nome.length() > 0){
										TextView resposta = (TextView) findViewById(R.id.tv_respostaNomeUsuario);
										resposta.setText(nome.toUpperCase());
									}
									Cadastro_usuarioActivity.this.removeDialog(NOME_DIALOG_ID);
								}
							}
					);
				dialog.setNegativeButton("Cancelar", new 
						DialogInterface.OnClickListener() {
							@SuppressLint("DefaultLocale")
							public void onClick(DialogInterface dialog,int which) {
								Cadastro_usuarioActivity.this.removeDialog(NOME_DIALOG_ID);
							}
						}
				);
				return dialog.create();
			}
			case (DATE_DIALOG_ID):{
				return new DatePickerDialog(this, dateSetListener, ano, mes,dia);
			}
		}
		return null;
	}
	
	protected void onActivityResult(int codigo, int resultado, Intent it){
		Bundle parans = null;
		if(codigo == LISTA_RELACIONAMENTO_ID && it != null){
			parans = it.getExtras();
			if(parans != null){
				String relacionamento = parans.getString("RELACIONAMENTO");
				TextView resposta	= (TextView) findViewById(R.id.tv_resposta_relacionamentoUsuario);
				resposta.setText(relacionamento);
			}
		}
		if(codigo == LISTA_NIVEL_ESCOLARIDADE_ID && it != null){
			parans = it.getExtras();
			if(parans != null){
				String nivelEscolar = parans.getString("NIVEL_ESCOLAR");
				TextView resposta	= (TextView) findViewById(R.id.tv_resposta_nivelEscolarUsuario);
				resposta.setText(nivelEscolar);
			}
		}
	}

	public void onClick(View v) {
		if(v == dataNascimento){
			showDialog(DATE_DIALOG_ID);
		}
		if(v == nomeUsuario){
			showDialog(NOME_DIALOG_ID);
		}
		if(v == tipoRelacionamento ){
			Intent it = new Intent("RELACIONAMENTO");
			it.addCategory("LISTA");
			startActivityForResult(it,LISTA_RELACIONAMENTO_ID);
		}
		if(v == escolaridade){
			Intent it = new Intent("NIVEL_ESCOLAR");
			it.addCategory("LISTA");
			startActivityForResult(it,LISTA_NIVEL_ESCOLARIDADE_ID);
		}
	}
}
