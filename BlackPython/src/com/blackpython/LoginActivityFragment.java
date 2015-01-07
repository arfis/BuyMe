package com.blackpython;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.data.UserInformation;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class LoginActivityFragment extends Fragment{
	ImageButton buttonfreelogin;
	LoginButton authButton;
	ProfilePictureView pf;
	private static final String TAG = "LoginActivityFragment";
	private UiLifecycleHelper uiHelper;
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
	        Bundle savedInstanceState) {
		
	    View view = inflater.inflate(R.layout.activitylogin, container, false);
	    
		pf = (ProfilePictureView) view.findViewById(R.id.profilePictureView1);
	    authButton = (LoginButton) view.findViewById(R.id.authButton);
	    
	    authButton.setFragment(this);
	    authButton.setReadPermissions(Arrays.asList("email"));
	    buttonfreelogin = (ImageButton) view.findViewById(R.id.buttonfreelogin);
	    addListenerOnButton(view);
	    return view;
	}
	
	
	public void addListenerOnButton(View v) {
		
		buttonfreelogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//Toast.makeText(getBaseContext(), "Button pressed!", Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Index.class);
				   Bundle b = new Bundle();
				   b.putInt("login", 0);
				   intent.putExtras(b); //Put your id to your next Intent
				   startActivity(intent);
			}
		});
	}
	
	//zmena stavu prihlasenia
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	Log.i(TAG, "Logged in...");
	        //make request to the /me API
	    	Request request = Request.newMeRequest(session,
	                new Request.GraphUserCallback() {
	                    // callback after Graph API response with user object

	    		//uspesne prihlasenie cez FB
	                    @Override
	                    public void onCompleted(GraphUser user,
	                            Response response) {
	                        // TODO Auto-generated method stub
	                        if (user != null) {
	                        	//pf.setProfileId(user.getId());
	                            Log.d("info:",user.getName() + ","
	                                    + user.getUsername() + ","
	                                    + user.getId() + "," + user.getLink()
	                                    + "," + user.getFirstName()+ "EMAIL: " +user.asMap().get("email"));
	                            
	                            //ziskanie informacii do statickej triedy UserInformation
	                            UserInformation.setLoggedIn(true);
	                            UserInformation.setLoggingButton(authButton);
	                            UserInformation.setName(user.getName());
	                            UserInformation.setId(user.getId());
	                            UserInformation.setEmail(user.asMap().get("email"));
	                            Intent intent = new Intent(getActivity(), Index.class);
	         				   	Bundle b = new Bundle();
	         				   	b.putInt("login", 1);
	         				   	intent.putExtras(b); //Put your id to your next Intent
	         				   	startActivity(intent);
	                        }
	                    }
	                    });
	        Request.executeBatchAsync(request);
	    }
	    
		else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	    }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
