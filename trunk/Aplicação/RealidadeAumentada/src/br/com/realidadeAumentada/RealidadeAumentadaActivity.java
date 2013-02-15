package br.com.realidadeAumentada;

import es.ucm.look.ar.LookAR;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import android.os.Bundle;

public class RealidadeAumentadaActivity extends LookAR {
	
	public RealidadeAumentadaActivity(){
		super(true,true,true,true,100.0f,true);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_main);
//        
        LookData.getInstance().setWorldEntityFactory(new EntityFactory(this));
        
        EntityData data = new EntityData();
        data.setLocation(-10, 0, 0);
        data.setPropertyValue(EntityFactory.NAME, "Element 1");
        data.setPropertyValue(EntityFactory.COLOR, "green");
        
        
        EntityData data1 = new EntityData();
        data1.setLocation(-10, -5, 5);
        data1.setPropertyValue(EntityFactory.NAME, "Element 2");
        data1.setPropertyValue(EntityFactory.COLOR, "red");
        
        LookData.getInstance().getDataHandler().addEntity(data);
        LookData.getInstance().getDataHandler().addEntity(data1);
        
        LookData.getInstance().updateData();
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
        
}