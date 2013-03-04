package br.com.realidadeAumentada;

import java.util.ArrayList;

import br.com.realidadeAumentada.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaRelacionamentoActivity extends Activity{

	private static String[] tiposRelacionemto = {"Solteiro (a)","Namorando","Casado (a)","Enrolado"};
	private static ListView listView;
	private static Integer posicao;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final ArrayList<String> relacionamentoList = createListaRelacionamento();
        listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,relacionamentoList);
        listView.setAdapter(adapter);
        if(posicao != null){
        	listView.setItemChecked(posicao, true);
        }
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				String relacionamento = relacionamentoList.get(arg2);
				Intent it = new Intent();
				it.putExtra("RELACIONAMENTO",relacionamento);
				
				setResult(0, it);
				finish();
			}
        });
	}
	
	private ArrayList<String> createListaRelacionamento(){
		ArrayList<String> p = new ArrayList<String>();
		
		p.add("Solteiro (a)"); 
		p.add("Namorando");
		p.add("Casado (a)");
		p.add("Enrolado");
		
		return p;
	}
	
	public static void setRelacionamento(String relacionamento){
		if(relacionamento != null){
			for (int i = 0; i < tiposRelacionemto.length; i++) {
				if(tiposRelacionemto[i].equals(relacionamento)){
					posicao = i;
					break;
				}
			}
		}
	}
}
