package com.blackpython;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {
	LoginActivityFragment loginf;
	Button buttonfreelogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        loginf = new LoginActivityFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, loginf)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        loginf = (LoginActivityFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	    addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		buttonfreelogin = (Button) findViewById(R.id.buttonfreelogin);
		buttonfreelogin.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Button pressed!", Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, Index.class);
				   Bundle b = new Bundle();
				   b.putInt("login", 0);
				   intent.putExtras(b); //Put your id to your next Intent
				   startActivity(intent);
			}
		});
	

	}
}
