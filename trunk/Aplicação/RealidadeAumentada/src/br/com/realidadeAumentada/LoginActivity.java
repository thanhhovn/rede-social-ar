package br.com.realidadeAumentada;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener{

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.login);

		Button btEntrar = (Button) findViewById(R.id.btEntrar);
		btEntrar.setOnClickListener(this);
	}

	public void onClick(View view) {
		Intent intent = new Intent("USUARIO");
		intent.addCategory("CADASTRO");
		startActivity(intent);
	}
}
