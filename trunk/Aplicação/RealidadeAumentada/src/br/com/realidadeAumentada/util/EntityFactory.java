package br.com.realidadeAumentada.util;
import android.content.Context;
import android.widget.Toast;
import es.ucm.look.ar.ar2D.drawables.Text2D;
import es.ucm.look.ar.ar3D.core.Color4;
import es.ucm.look.ar.ar3D.core.drawables.Entity3D;
import es.ucm.look.ar.ar3D.core.drawables.primitives.Cube;
import es.ucm.look.ar.listeners.TouchListener;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.WorldEntity;
import es.ucm.look.data.WorldEntityFactory;


public class EntityFactory extends WorldEntityFactory{
	
	public static final String NAME="name";
	public static final String COLOR = "color";
	private Context contexto;
	
	public static Double origenX;// pegar da Classe Location
	public static Double origenY;
	
	public EntityFactory(Context contexto){
		this.contexto = contexto;
	}
	
	@Override
	public WorldEntity createWorldEntity(EntityData data){
		WorldEntity we = new WorldEntity(data);
		we.setDrawable2D(new Text2D(data.getPropertyValue(NAME)));
			
		Entity3D drawable3d = new Entity3D(new Cube());
		
		String color = data.getPropertyValue(COLOR);
		if(color.equals("red")){
			drawable3d.setMaterial(new Color4(1.0f,0.0f,0.0f));
			
		}else
		if(color.equals("green")){
			drawable3d.setMaterial(new Color4(0.0f,1.0f,0.0f));
		}
		we.setDrawable3D(drawable3d);
		we.addTouchListener(new TouchListener(){

			public boolean onTouchDown(WorldEntity arg0, float arg1, float arg2) {
				Toast.makeText(contexto,"Sucesso Down",Toast.LENGTH_LONG).show();
				return false;
			}

			public boolean onTouchMove(WorldEntity arg0, float arg1, float arg2) {
				Toast.makeText(contexto,"Sucesso Move",Toast.LENGTH_LONG).show();
				return false;
			}

			public boolean onTouchUp(WorldEntity arg0, float arg1, float arg2) {
				Toast.makeText(contexto,"Sucesso Up",Toast.LENGTH_LONG).show();
				return false;
			}
			
		});
		return we;
	}
	
	public static float distanciaEntrePonto(Double bx,Double by){
		Double origenXporax =  Math.pow((origenX-bx),2);
		Double origenYporay =  Math.pow((origenY-by),2);
		Double distancia = Math.sqrt(origenXporax+origenYporay);
		double teta = Math.cos(bx)/Math.sin(by);
		Double angulo =  (distancia*Math.sin(teta));
		
		return angulo.floatValue();
	}
	
	public static float distanciaEntrePonto2(Double bx,Double by){
		Double origenXporax =  Math.pow((origenX-bx),2);
		Double origenYporay =  Math.pow((origenY-by),2);
		Double distancia = Math.sqrt(origenXporax+origenYporay);
		double teta = Math.cos(origenX)/Math.sin(origenY);
		Double angulo =  (distancia*Math.sin(teta));
		
		return angulo.floatValue();
	}
	
    public static void transformarCorEsfericaRetangulares(){
    	double raio =  Math.sqrt( (Math.pow(origenX, 2)+ Math.pow(origenY, 2)+1) );
    	double teta =  Math.atan2(origenY,origenX);
    	double zeta =   Math.atan2(Math.sqrt( (Math.pow(origenX, 2)+ Math.pow(origenY, 2)) ),1);
    	
    	origenX =  (raio*Math.sin(teta)*Math.cos(zeta));
    	origenY =  (raio*Math.sin(teta)*Math.sin(zeta));	    	    	
    }
}
