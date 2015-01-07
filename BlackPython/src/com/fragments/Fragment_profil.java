package com.fragments;

import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;
import com.data.UserInformation;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_profil extends Fragment{
	ProfilePictureView pf;
	TextView tv3,tv2,tv;
	LoginButton lb;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View v;
		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_user, container, false);
		tv = (TextView) v.findViewById(R.id.textView4);
		tv2 = (TextView) v.findViewById(R.id.textView5);
		tv3 = (TextView) v.findViewById(R.id.textView2);
		pf = (ProfilePictureView)v.findViewById(R.id.profilePictureView1);
		lb = (LoginButton) v.findViewById(R.id.loginButton1);
		if(UserInformation.isLoggedIn())
		{
		lb = UserInformation.getLoggingButton();
		tv.setText("Meno:  " + UserInformation.getName());
		tv2.setText("Email:  " + UserInformation.getEmail().toString());
		tv3.setText("Počet použitých kupónov:  " + UserInformation.getUsedCoupons());
		pf.setProfileId(UserInformation.getId());
		}
		
		return v;
	    }
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        //make request to the /me API
	    	Request request = Request.newMeRequest(session,
	                new Request.GraphUserCallback() {
	                    // callback after Graph API response with user object

	                    @Override
	                    public void onCompleted(GraphUser user,
	                            Response response) {
	                        // TODO Auto-generated method stub
	                        if (user != null) {
	                        	pf.setProfileId(user.getId());
	                            Log.d("info:",user.getName() + ","
	                                    + user.getUsername() + ","
	                                    + user.getId() + "," + user.getLink()
	                                    + "," + user.getFirstName()+ "EMAIL: " +user.asMap().get("email"));
	                            
	                            //ziskanie informacii do statickej triedy UserInformation
	                            UserInformation.setLoggedIn(true);
	                            UserInformation.setLoggingButton(lb);
	                            UserInformation.setName(user.getName());
	                            UserInformation.setId(user.getId());
	                            UserInformation.setEmail(user.asMap().get("email"));
	                        }
	                    }
	                    });
	        Request.executeBatchAsync(request);
	    }
	    
		else if (state.isClosed()) {
			pf.setProfileId(null);
	        tv.setText("");
	        tv2.setText("");
	    }
	}
}
