package com.fragments;

import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;
import data.UserInformation;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.content.Intent;
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
	private static final String TAG = "LoginActivityFragment";
	private UiLifecycleHelper uiHelper;

	
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
		fillInformation();
		}
		else fillNone();

		return v;
	    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	private void fillInformation(){
		lb = UserInformation.getLoggingButton();
		tv.setText("Meno:  " + UserInformation.getName());
		tv2.setText("Email:  " + UserInformation.getEmail().toString());
		tv3.setText("Počet použitých kupónov:  " + UserInformation.getUsedCoupons());
		pf.setProfileId(UserInformation.getId());
	}
	
	private void fillNone(){
		lb = UserInformation.getLoggingButton();
		tv.setText("");
		tv2.setText("");
		tv3.setText("");
		pf.setProfileId(null);
	}
}
