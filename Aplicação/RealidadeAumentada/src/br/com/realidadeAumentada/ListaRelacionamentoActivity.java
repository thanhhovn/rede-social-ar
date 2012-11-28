package br.com.realidadeAumentada;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaRelacionamentoActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final ArrayList<String> relacionamentoList = createListaRelacionamento();
        ListView listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,relacionamentoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				System.out.println("Entrou");
				String relacionamento = relacionamentoList.get(arg2);
				Intent it = new Intent();
				it.putExtra("relacionamento",relacionamento);
				
				setResult(1, it);
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
}
