package com.blackpython;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity {
	LoginActivityFragment loginf;
	Button buttonfreelogin;
	private static final String TAG = "MainFragment";
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
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
	}

}
