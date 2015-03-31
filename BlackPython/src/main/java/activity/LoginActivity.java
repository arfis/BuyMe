package activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends FragmentActivity {
	LoginActivityFragment loginf;
	Button buttonfreelogin;
	private static final String TAG = "MainFragment";
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"activity",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

	    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

	    //getActionBar().hide();
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
