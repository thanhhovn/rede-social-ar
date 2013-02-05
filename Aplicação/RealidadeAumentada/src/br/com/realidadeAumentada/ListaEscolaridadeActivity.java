package br.com.realidadeAumentada;

import java.util.ArrayList;

import br.com.realidadeAumentada.R;
import br.com.realidadeAumentada.R.id;
import br.com.realidadeAumentada.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListaEscolaridadeActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final ArrayList<String> escolaridadeList = createListaEscolaridade();
        ListView listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,escolaridadeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				String escolaridade = escolaridadeList.get(arg2);
				Intent it = new Intent();
				it.putExtra("NIVEL_ESCOLAR",escolaridade);
				
				setResult(1, it);
				finish();
			}
        });
	}
	
	private ArrayList<String> createListaEscolaridade(){
		ArrayList<String> p = new ArrayList<String>();
		
		p.add("Fundamental");
		p.add("Fundamental Incompleto");
		p.add("Médio");
		p.add("Médio Incompleto");
		p.add("Superior");
		p.add("Superior Incompleto");
		
		return p;
	}
}
