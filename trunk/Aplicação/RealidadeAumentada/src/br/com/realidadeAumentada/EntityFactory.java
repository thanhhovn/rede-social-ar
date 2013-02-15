package br.com.realidadeAumentada;
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
	
	public EntityFactory(Context contexto){
		this.contexto = contexto;
	}
	
	@Override
	public WorldEntity createWorldEntity(EntityData data){
		WorldEntity we = new WorldEntity(data);
		we.setDrawable2D(new Text2D(data.getPropertyValue(NAME)));
			
		//Entity3D drawable3d = new Entity3D(new Cube());
		
		String color = data.getPropertyValue(COLOR);
		if(color.equals("red")){
			//drawable3d.setMaterial(new Color4(1.0f,0.0f,0.0f));
		}else
		if(color.equals("green")){
			//drawable3d.setMaterial(new Color4(0.0f,1.0f,0.0f));
		}
		//we.setDrawable3D(drawable3d);
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
}
