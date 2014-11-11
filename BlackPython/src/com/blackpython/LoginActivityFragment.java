package com.blackpython;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.blackpython.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class LoginActivityFragment extends Fragment{
	Button buttonfreelogin;
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activitylogin, container, false);

	    return view;
	}
	
}
