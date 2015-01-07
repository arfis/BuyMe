package com.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.blackpython.ActivityFullscreen;
import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;
import com.data.Coupon;
import com.data.CouponSet;
import com.data.MemoryStorage;
import com.data.UserInformation;
import com.gui.ButtonCoupon;

public class Fragment_coupons extends Fragment implements View.OnClickListener{
	boolean firstTime = true;
	int pressed;
	public static ButtonCoupon[] coupons = new ButtonCoupon[10];
	View v;
	
	ImageButton 
			button1,
			button2,
			button3,
			button4,
			button5,
			button6,
			button7,
			button8,
			button9,
			button10;
    ImageButton imagebuttons[]={ button1,button2,button3,button4,button5,
    		button6,button7,button8,button9,button10};
    
    int ids[]={R.id.ImageButton1,R.id.ImageButton2,R.id.ImageButton3,R.id.ImageButton4,R.id.ImageButton5
		,R.id.ImageButton6,R.id.ImageButton7,R.id.ImageButton8,R.id.ImageButton9
		,R.id.ImageButton10};
	MemoryStorage coup;
	private DrawerLayout drawerLayout;
	private String[] drawerListViewItems;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    
    public void init()
	{
			coupons[0] = new ButtonCoupon();
			coupons[1] = new ButtonCoupon();
			coupons[2] = new ButtonCoupon();
			coupons[3] = new ButtonCoupon();
			coupons[4] = new ButtonCoupon();
			coupons[5] = new ButtonCoupon();
			coupons[6] = new ButtonCoupon();
			coupons[7] = new ButtonCoupon();
			coupons[8] = new ButtonCoupon();
			coupons[9] = new ButtonCoupon();
			
		  	coupons[0].setImageBut(R.id.ImageButton1);
	        coupons[1].setImageBut(R.id.ImageButton2);
	        coupons[2].setImageBut(R.id.ImageButton3);
	        coupons[3].setImageBut(R.id.ImageButton4);
	        coupons[4].setImageBut(R.id.ImageButton5);
	        coupons[5].setImageBut(R.id.ImageButton6);
	        coupons[6].setImageBut(R.id.ImageButton7);
	        coupons[7].setImageBut(R.id.ImageButton8);
	        coupons[8].setImageBut(R.id.ImageButton9);
	        coupons[9].setImageBut(R.id.ImageButton10);
	        
		
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		init();
		v = inflater.inflate(R.layout.fragment_coupons, container, false);
		//adding the listener to the buttons - different style, cause it is in a fragment
		for(int i=0;i<imagebuttons.length;i++)
        {
            imagebuttons[i]=(ImageButton) v.findViewById(ids[i]);	
            imagebuttons[i].setOnClickListener(this);
        }
		buttonCheck();
		return v;
    }
        	
    @Override
    public void onPause()
    {
    	super.onPause();
    	//this.getIntent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }
 
    @Override
	public void onClick(View arg0) {
        Intent intent = new Intent(getActivity(), ActivityFullscreen.class);
        Bundle b = new Bundle();
        UserInformation.CouponIncrement();
        b.putInt("key", arg0.getId());
		pressed = arg0.getId();
		intent.putExtras(b); //Put your id to your next Intent
		startActivity(intent);
			}
    
    //when going back from the activity, resume is called and not all buttons should be there
    	@Override
         public void onResume()
         {  	
           	super.onResume();
           	buttonCheck();
    	 }
    
        public void buttonCheck()
        {
        int gone = 0;
        for(int i=0;i<10;i++)
	    {    		 
	        ImageButton ib=(ImageButton) v.findViewById(coupons[i].getImageBut());	
	        //vyberaju sa kupony v pamati - z databazy
	        if(CouponSet.getCoupons().get(i).isUsed() == 1)
	        {
	        	  ib.setVisibility(View.GONE);
	        	  gone++;			 
	        }
	    }
	    	if(gone == 10)
	        {
	         	ImageView sold = (ImageView) v.findViewById(R.id.imageView1);
	         	sold.setVisibility(View.VISIBLE);
	        }
	         	Log.d("RESUME","nastal resume");  
		}
}
