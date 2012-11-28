package br.com.realidadeAumentada;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import br.com.realidadeAumentada.listas.Relacionamento;


public class RelacionamentoAdapter extends ArrayAdapter<Relacionamento> {

	private Context context;
	private List<Relacionamento> relacionamentos;
	private int view;
	
	public RelacionamentoAdapter(Context context, int view, List<Relacionamento> relacionamentos) {
		super(context, view, relacionamentos);
		this.context = context;
		this.relacionamentos = relacionamentos;
		this.setView(view);
	}

	public int getCount() {
		return relacionamentos.size();
	}

	public Relacionamento getItem(int position) {
		return relacionamentos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = null;
	
	    if(convertView == null){
	        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = inflater.inflate(R.layout.lista_relacionamento,parent,false);
	        final RelacionamentoAntigo viewHolder = new RelacionamentoAntigo();
	        viewHolder.tipo = (TextView) v.findViewById(R.id.tv_tipoRelcionamentoUsuario);
	        viewHolder.marcado = (RadioButton) v.findViewById(R.id.rd_tipoRelcionamentoUsuario);
	        viewHolder.marcado.setId(position);
	        viewHolder.marcado.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	                Relacionamento element = (Relacionamento) viewHolder.marcado.getTag();
	                element.setMarcado(buttonView.isChecked());
	            }
	        });
	        v.setTag(viewHolder);
	        viewHolder.marcado.setTag(relacionamentos.get(position));
	    }else{
	        v = convertView;
	        ((RelacionamentoAntigo)v.getTag()).marcado.setTag(relacionamentos.get(position));
	    }
	    RelacionamentoAntigo holder = (RelacionamentoAntigo) v.getTag();
	    holder.tipo.setText(relacionamentos.get(position).getName());
	    holder.marcado.setChecked(relacionamentos.get(position).isMarcado());
	    
	    return v;
	}
	
	public void setView(int view) {
		this.view = view;
	}

	public int getView() {
		return view;
	}

	static class RelacionamentoAntigo{
		TextView tipo;
		RadioButton marcado;
	}
}
