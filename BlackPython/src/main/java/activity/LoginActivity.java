package activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.blackpython.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import data.UserInformation;
import manager.SharedPreferencesManager;
import utils.LoggingTypes;

public class LoginActivity extends FragmentActivity
{
	LoginActivityFragment loginf;

    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(android.R.id.content);
        if (fragment != null)
        {
            ((LoginActivityFragment)fragment).onActivityResult(requestCode, resultCode,data);
        }
    }

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
            UserInformation.init(this);
            int loggedMethod = UserInformation.getLoggedMethod();

            if (loggedMethod == LoggingTypes.GMAIL.getIntValue())
            {
                Intent intent = new Intent(this,GoogleLogin.class);
                startActivity(intent);
                this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                this.finish();
            }
            else if (loggedMethod == LoggingTypes.FACEBOOK.getIntValue())
            {
                 Intent intent = new Intent(this,Index.class);
                 startActivity(intent);
                 this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                 this.finish();
            }
            else
            {
                loginf = new LoginActivityFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, loginf)
                        .commit();
            }
	    }
        else
        {
	        // Or set the fragment from restored state info
	        loginf = (LoginActivityFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	}
}
