package br.com.realidadeAumentada;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.realidadeAumentada.cadastroUsuario.Usuario;
import br.com.realidadeAumentada.util.Endereco;
import br.com.realidadeAumentada.util.Validador;
import br.com.realidadeAumentada.validador.Mask;
import br.com.realidadeAumentada.webService.MetodosWBS;
import br.com.realidadeAumentada.webService.MontandoChamadaWBS;

public class Perfil_usuarioActivity extends Activity implements OnClickListener, OnCheckedChangeListener {

	private static final int LISTA_RELACIONAMENTO_ID = 0;
	private static final int LISTA_NIVEL_ESCOLARIDADE_ID = 1;
	private static final int TIME_DIALOG_ID = 2;
	private static final int DATE_DIALOG_ID = 3;
	private static final int NOME_DIALOG_ID = 4;
	
	private String sexo                         = null;
	
	private Button cancelar                     = null;
	private Button confirmar                    = null;
	private TextView respostaData               = null;
	private TextView nomeUsuario                = null;
	private EditText profissao                  = null;
	private EditText telefone                   = null;
	private EditText bairro                     = null;
	private EditText logradouro                 = null;
	private EditText numeroCasa                 = null;
	private TextView tipoRelacionamento         = null;
	private TextView nivel_escolar              = null;
	private LinearLayout layoutUsuario          = null;
	private RadioGroup grupoSexo                = null;
	private Spinner pais                        = null;
	private Spinner estado                      = null;
	private Spinner cidade                      = null;
	private LinearLayout dataNascimento         = null;
	private LinearLayout telaTipoRelacionamento = null;
	private LinearLayout escolaridade           = null;
	
	private static Integer posicaoEstado;
	//Variaveis para a Data
	private int ano;
    private int mes;
    private int dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfil_usuario);
		
		layoutUsuario	= (LinearLayout) findViewById(R.id.layoutNomeUsuario);
		grupoSexo = (RadioGroup) findViewById(R.id.layout_rdGrupo_SexoUsuario);
		telaTipoRelacionamento	= (LinearLayout) findViewById(R.id.layoutRelacionamentoUsuario);
		escolaridade	= (LinearLayout) findViewById(R.id.layoutEscolaridadeUsuario);
		dataNascimento = (LinearLayout) findViewById(R.id.layoutDataNascimentoUsuario);
		respostaData = (TextView) findViewById(R.id.tv_RespostadataNascimentoUsuario);
		telefone = (EditText) findViewById(R.id.ed_telefoneUsuario);
		dataNascimento = (LinearLayout) findViewById(R.id.balloon_main_layout);
//		dataNascimento = (View) findViewById(R.id.balloon_inner_layout);
//		dataNascimento = (View) findViewById(R.id.balloon_close);
//		dataNascimento = (View) findViewById(R.id.balloon_disclosure);
//		nomeUsuario = (TextView) findViewById(R.id.balloon_item_title);
//		nomeUsuario = (TextView) findViewById(R.id.balloon_item_snippet);
		bairro = (EditText) findViewById(R.id.et_bairro);
		logradouro = (EditText) findViewById(R.id.et_logradouro);
		numeroCasa = (EditText) findViewById(R.id.et_numeroCasa);
		
		pais   = (Spinner) findViewById(R.id.pais);
		estado = (Spinner) findViewById(R.id.estado);
		cidade = (Spinner) findViewById(R.id.cidade);
		
		nomeUsuario = (TextView) findViewById(R.id.tv_respostaNomeUsuario);

		tipoRelacionamento	= (TextView) findViewById(R.id.tv_resposta_relacionamentoUsuario);
		nivel_escolar	= (TextView) findViewById(R.id.tv_resposta_nivelEscolarUsuario);
		
		telefone.addTextChangedListener(Mask.insert("(##)####-####", telefone));
		profissao = (EditText) findViewById(R.id.et_profissaoUsuario); 
		
		cancelar = (Button) findViewById(R.id.btCancelarPerfil);
		confirmar = (Button) findViewById(R.id.btEntrarPerfil);
		
		confirmar.setOnClickListener(this);
		cancelar.setOnClickListener(this);
		layoutUsuario.setOnClickListener(this);
		grupoSexo.setOnCheckedChangeListener(this);
		dataNascimento.setOnClickListener(this);
		telaTipoRelacionamento.setOnClickListener(this);
		escolaridade.setOnClickListener(this);
		ocultarTeclado(profissao);
		if(Usuario.getUsuarioLogado_id() != null && !Usuario.getUsuarioLogado_id().equals(null)){
			carregarDadosEdicao();
		}else{
			carregaData();
		}
		
		List<String> listaPais = new ArrayList<String>();
		listaPais.add("Brasil");
		pais.setAdapter(adapter(listaPais));
		pais.setEnabled(false);
		pais.setClickable(false);
		pais.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) { }
            public void onItemSelected(AdapterView<?> parent, View v,
              int position, long id) { }
		});
		carregaListaCidades(false,null);
		
		List<String> listaEstados = Endereco.getEstados();
		estado.setAdapter(adapter(listaEstados));
		estado.setOnItemSelectedListener(new OnItemSelectedListener() {
		              public void onNothingSelected(AdapterView<?> arg0) { }
		              public void onItemSelected(AdapterView<?> parent, View v,
		                int position, long id) {
		            	  String estadoTemp = Usuario.dadosPerfil.getEndereco().getNome_Estado();
			            	  if(position != 0){
			            			  if(posicaoEstado != null){
			            				  carregaListaCidades(true,Long.valueOf(posicaoEstado));
				            			  String cidadeTemp = Usuario.dadosPerfil.getEndereco().getNome_cidade();
				            			  int posicaoCidade = Endereco.cidadeIndice(cidadeTemp);
				            			  cidade.setSelection(posicaoCidade);
				            			  posicaoEstado = null;
			            			  }else{
			            				  carregaListaCidades(true,parent.getSelectedItemId());
			            			  }
			            	  }else{
			            		  if(estadoTemp != null){
			            			  posicaoEstado = Endereco.estadoIndice(estadoTemp);
			            			  estado.setSelection(posicaoEstado);
			            		  }else{
			            			  carregaListaCidades(false,parent.getSelectedItemId());
			            		  }
			            	  }
		              }
		});
		
		cidade.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> arg0) {  }
            public void onItemSelected(AdapterView<?> parent, View v,
              int position, long id) {
            	if(position != 0){
            		Long codCidade = parent.getSelectedItemId();
                	String cod_cidade = String.valueOf(codCidade.intValue());
                	Usuario.dadosPerfil.getEndereco().setCod_cidade(cod_cidade);
            		controleCamposEndereco(true);
          	  	}else{
          	  		controleCamposEndereco(false);
          	  	}
            }
		});
	}
	
	private void controleCamposEndereco(boolean flag){
		bairro.setEnabled(flag);
		logradouro.setEnabled(flag);
		numeroCasa.setEnabled(flag);
		bairro.setClickable(flag);
		logradouro.setClickable(flag);
		numeroCasa.setClickable(flag);
	}
	
	private void carregaListaCidades(boolean flag,Long idEstado){
		List<String> listaCidades = new ArrayList<String>();
		if(flag){
			if(idEstado != null){
				cidade.setEnabled(flag);
				cidade.setClickable(flag);
				listaCidades = Endereco.getCidades(idEstado);
				cidade.setAdapter(adapter(listaCidades));
				boolean flagTemp = posicaoEstado == null? true:false;
				limparCamposEndereco(flagTemp);
			}
		}else{
			controleCamposEndereco(flag);
			cidade.setEnabled(flag);
			cidade.setClickable(flag);
			cidade.setAdapter(adapter(listaCidades));
		}
	}
	
	private ArrayAdapter<String> adapter(List<String> dado){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dado);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		return adapter; 
	}
	
	private void carregaData(){
	      final Calendar c = Calendar.getInstance();
	        ano = c.get(Calendar.YEAR);
	        mes = c.get(Calendar.MONTH);
	        dia = c.get(Calendar.DAY_OF_MONTH);
	}
	
	private void carregarDadosEdicao(){
			nomeUsuario.setText(Usuario.dadosPerfil.getNome());
			
			grupoSexo.clearCheck();
			if(Usuario.dadosPerfil.getSexo() == 'M'){
				grupoSexo.check(R.id.rd_masculino);
			}else{
				grupoSexo.check(R.id.rd_feminino);
			}
			
			String[] data = Usuario.dadosPerfil.getDt_nascimento().split("-");
			dia = Integer.valueOf(data[2]);
			mes = Integer.valueOf(data[1])-1;
			ano = Integer.valueOf(data[0]);
			respostaData.setText(dia+"-"+(mes+1)+"-"+ano);
			tipoRelacionamento.setText(Usuario.dadosPerfil.getStatus_relacionamento());
			nivel_escolar.setText(Usuario.dadosPerfil.getNivel_escolar());
			profissao.setText(Usuario.dadosPerfil.getProfissao());
			telefone.setText(Usuario.dadosPerfil.getTelefone());
			bairro.setText(Usuario.dadosPerfil.getEndereco().getNome_bairro());
			logradouro.setText(Usuario.dadosPerfil.getEndereco().getNome_logradouro());
			numeroCasa.setText(Usuario.dadosPerfil.getEndereco().getNumero_endereco());
	}
	
	private void ocultarTeclado(EditText edit){
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//edit.setFocusableInTouchMode(false); // Passa o foco para o próximo componente
	}
	
	private void mostrarTeclado(EditText edit){
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	
	// Atualiza a data no TextView
    private void updateDisplay() {
    	respostaData.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(dia).append("-")
                    .append(mes+1).append("-")
                    .append(ano));
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
				editNome.setText(nomeUsuario.getText());
				dialog.setPositiveButton("OK", new 
						DialogInterface.OnClickListener() {
							@SuppressLint("DefaultLocale")
							public void onClick(DialogInterface dialog,int which) {
								
								String nome	= editNome.getText().toString();
								if(nome != null && nome.length() > 0){
									nomeUsuario.setText(nome.toUpperCase());
								}
								Perfil_usuarioActivity.this.removeDialog(NOME_DIALOG_ID);
							}
						}
				);
				dialog.setNegativeButton("Cancelar", new 
						DialogInterface.OnClickListener() {
							@SuppressLint("DefaultLocale")
							public void onClick(DialogInterface dialog,int which) {
								Perfil_usuarioActivity.this.removeDialog(NOME_DIALOG_ID);
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
	
	// TODO Ainda não esta sendo utilizado
	private void limparCampos(){
		respostaData = null;
		profissao = null;
		telefone = null;
		layoutUsuario = null;
		grupoSexo = null;
		dataNascimento = null;
		telaTipoRelacionamento = null;
		escolaridade = null;
	}
	
	private void limparCamposEndereco(boolean flag){
		if(flag){
			bairro.setText("");
			logradouro.setText("");
			numeroCasa.setText("");
		}
	}
	
	protected void onActivityResult(int codigo, int resultado, Intent it){
		Bundle parans = null;
		if(codigo == LISTA_RELACIONAMENTO_ID && it != null){
			parans = it.getExtras();
			if(parans != null){
				String relacionamento = parans.getString("RELACIONAMENTO");
				tipoRelacionamento.setText(relacionamento);
			}
		}
		if(codigo == LISTA_NIVEL_ESCOLARIDADE_ID && it != null){
			parans = it.getExtras();
			if(parans != null){
				String escolaridade = parans.getString("NIVEL_ESCOLAR");
				nivel_escolar.setText(escolaridade);
			}
		}
	}

	public void onClick(View v) {
		if(v == dataNascimento){
			showDialog(DATE_DIALOG_ID);
		}
		if(v == layoutUsuario){
			showDialog(NOME_DIALOG_ID);
		}
		if(v == telaTipoRelacionamento ){
			String relacionamento = null;
			if(tipoRelacionamento.getText() != null && tipoRelacionamento.getText().toString().length() > 0){
				relacionamento = tipoRelacionamento.getText().toString();
			}else{
				relacionamento = Usuario.dadosPerfil.getStatus_relacionamento();
			}
			ListaRelacionamentoActivity.setRelacionamento(relacionamento);
			Intent it = new Intent("RELACIONAMENTO");
			it.addCategory("LISTA");
			startActivityForResult(it,LISTA_RELACIONAMENTO_ID);
		}
		if(v == escolaridade){
			String escolaridade = null;
			if(nivel_escolar.getText() != null && nivel_escolar.getText().toString().length() > 0){
				escolaridade = nivel_escolar.getText().toString();
			}else{
				escolaridade = Usuario.dadosPerfil.getNivel_escolar();
			}
			ListaEscolaridadeActivity.setEscolaridade(escolaridade);
			Intent it = new Intent("NIVEL_ESCOLAR");
			it.addCategory("LISTA");
			startActivityForResult(it,LISTA_NIVEL_ESCOLARIDADE_ID);
		}
		if(v == confirmar){
			if(Usuario.getUsuarioLogado_id() == null){
				String nome_login = Usuario.dadosLogin.getNome_login();
				String email = Usuario.dadosLogin.getEmail();
				String senha = Usuario.dadosLogin.getSenha();
			
				Usuario.dadosLogin.setNome_login(nome_login);
				Usuario.dadosLogin.setEmail(email);
				Usuario.dadosLogin.setSenha(senha);
				Usuario.dadosPerfil.setNome(nomeUsuario.getText().toString());

				Usuario.dadosPerfil.setSexo(getSexo());
				Usuario.dadosPerfil.setDt_nascimento(respostaData.getText().toString());
				Usuario.dadosPerfil.setStatus_relacionamento(tipoRelacionamento.getText().toString());
				Usuario.dadosPerfil.setNivel_escolar(nivel_escolar.getText().toString());
				Usuario.dadosPerfil.setProfissao(profissao.getText().toString());
				Usuario.dadosPerfil.setTelefone(telefone.getText().toString());
			
				Usuario.dadosPerfil.getEndereco().setNome_bairro(bairro.getText().toString());
				Usuario.dadosPerfil.getEndereco().setNome_logradouro(logradouro.getText().toString());
				Usuario.dadosPerfil.getEndereco().setNumero_endereco(numeroCasa.getText().toString());
				
				montarDados(false);
				
			}else{
				Usuario.dadosPerfil.setNome(nomeUsuario.getText().toString());
				Usuario.dadosPerfil.setSexo(getSexo());
				Usuario.dadosPerfil.setDt_nascimento(respostaData.getText().toString());
				Usuario.dadosPerfil.setStatus_relacionamento(tipoRelacionamento.getText().toString());
				Usuario.dadosPerfil.setNivel_escolar(nivel_escolar.getText().toString());
				Usuario.dadosPerfil.setProfissao(profissao.getText().toString());
				Usuario.dadosPerfil.setTelefone(telefone.getText().toString());
			
				Usuario.dadosPerfil.getEndereco().setNome_bairro(bairro.getText().toString());
				Usuario.dadosPerfil.getEndereco().setNome_logradouro(logradouro.getText().toString());
				Usuario.dadosPerfil.getEndereco().setNumero_endereco(numeroCasa.getText().toString());
				
				montarDados(true);
			}
			this.finish();
			Intent it = new Intent("TELA_LOGIN");
			it.addCategory("LOGIN");
			startActivity(it);
		}
		if(v == cancelar){
			Perfil_usuarioActivity.this.finish();
		}
	}
	
	private void montarDados(boolean editavel){
		StringBuilder dados = null;
		if(!editavel){
			dados = new StringBuilder();
			dados.append(Usuario.dadosPerfil.getEndereco().getCod_cidade()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNome_bairro()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNome_logradouro()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNumero_endereco()+",");
			
			dados.append(Usuario.dadosLogin.getEmail()+",");
			dados.append(Usuario.dadosPerfil.getNome()+",");
			dados.append(Usuario.dadosPerfil.getSexo()+",");
			dados.append(Usuario.dadosPerfil.getStatus_relacionamento()+",");
			dados.append(Usuario.dadosPerfil.getNivel_escolar()+",");
			dados.append(Usuario.dadosPerfil.getProfissao()+",");
			dados.append(Usuario.dadosPerfil.getTelefone()+",");
			dados.append(Usuario.dadosPerfil.getDt_nascimento()+",");
			
			dados.append(Usuario.dadosLogin.getSenha()+",");
			dados.append(Usuario.dadosLogin.getNome_login());
			gravarDadosUsuario(dados.toString());
			
		}else{
			dados = new StringBuilder();
			dados.append(Usuario.dadosPerfil.getEndereco().getCod_cidade()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNome_bairro()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNome_logradouro()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getNumero_endereco()+",");
			dados.append(Usuario.dadosPerfil.getEndereco().getEnderecoId()+",");
			
			dados.append(Usuario.dadosPerfil.getNome()+",");
			dados.append(Usuario.dadosPerfil.getSexo()+",");
			dados.append(Usuario.dadosPerfil.getStatus_relacionamento()+",");
			dados.append(Usuario.dadosPerfil.getNivel_escolar()+",");
			dados.append(Usuario.dadosPerfil.getProfissao()+",");
			dados.append(Usuario.dadosPerfil.getTelefone()+",");
			dados.append(Usuario.dadosPerfil.getDt_nascimento()+",");
			dados.append(Usuario.getUsuarioLogado_id());

			editarPerfilUsuario(dados.toString());
		}
	}
	
	private void gravarDadosUsuario(String dadosUsuario){
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.CADASTRAR_USUARIO);
			
			chamaWBS.addParametro(dadosUsuario);
			
			String msg = null;
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo!=null && !spo.toString().equals("ERRO")){
				msg="Cadastrado com Sucesso.";
			}else{
				msg="Não foi possível se conectar com o servidor. Tente mais tarde.";
			}
			Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
			toast.show();
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
	}
	
	private void editarPerfilUsuario(String dadosUsuario){
		try{
			MontandoChamadaWBS chamaWBS = new MontandoChamadaWBS();
			chamaWBS.setMetodo(MetodosWBS.EDITAR_PERFIL);
			
			chamaWBS.addParametro(dadosUsuario);
			String msg = null;
			Object  spo = (Object) chamaWBS.iniciarWBS();
			if(spo!=null && !spo.toString().equals("ERRO")){
				msg="Perfil Alterado com Sucesso.";
			}else{
				msg="Não foi possível se conectar com o servidor. Tente mais tarde.";
			}
			Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
			toast.show();
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rd_masculino) {
			setSexo("M");
	     }
		if (checkedId == R.id.rd_feminino) {
			setSexo("F");
	     }
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
