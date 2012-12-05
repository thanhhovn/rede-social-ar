package br.com.realidadeAumentada;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MenuActivity extends Activity {

	// Define the new menu item identifiers
	  static final private int ADD_NEW_TODO = Menu.FIRST;
	  static final private int REMOVE_TODO = Menu.FIRST + 1;	
	  

	  /** Chamado quando o Activity eh criado */
	  @Override
	  public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	  }
	     
	  
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);      
	    
	    // Create and add new menu items.  
	    MenuItem itemAdd = menu.add(0, ADD_NEW_TODO, Menu.NONE, R.string.add_new);
	    MenuItem itemRem = menu.add(0, REMOVE_TODO, Menu.NONE, R.string.remove);

	    // Assign icons
	    itemAdd.setIcon(R.drawable.add_new_item);
	    itemRem.setIcon(R.drawable.remove_item);

	    // Allocate shortcuts to each of them.
	    itemAdd.setShortcut('0', 'a');
	    itemRem.setShortcut('1', 'r');

	    return true;
	  }

	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
		return false;
	}
}
