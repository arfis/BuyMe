package com.fragments;

import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_info extends Fragment{
	View v;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
		
			v = inflater.inflate(R.layout.fragment_info, container, false);
			TextView tv1 = (TextView)v.findViewById(R.id.textView1);
			tv1.setText("Info");
			TextView tv2 = (TextView)v.findViewById(R.id.textView2);
			tv2.setText("Neviem co sem mam napisat, mozno by tu bol pekny aj nejaky obrazok alebo tak nejaky design spravit aj na tieto okna");
			return v;
	    }
}
