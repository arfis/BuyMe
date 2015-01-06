package com.blackpython;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Rules_fragment extends Fragment{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		View v;
	        // Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_rules, container, false);
		TextView tv1 = (TextView)v.findViewById(R.id.textView1);
		tv1.setText("Pravidlá");
		TextView tv2 = (TextView)v.findViewById(R.id.textView2);
		tv2.setText("Blablabla pravidla každý kupón možno použiť iba raz");
		return v;
	    }
}
