package com.blackpython;

import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.blackpython.R;
import com.data.Coupon;
import com.data.CouponSet;
import com.data.MemoryStorage;
import com.data.UserInformation;
import com.fragments.Fragment_coupons;

//aktivita pre zobrazovanie informacii o kuponov
public class ActivityFullscreen extends Activity implements AnimationListener {
	ImageView img,bt;
	RatingBar rb;
	TextView tv,tv2;
	Button but,button;
	MemoryStorage db;
	int value;
	int index;
	String instance;
	Animation rotation,fadeIn;
	boolean gold = false;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        db = UserInformation.getMemory();
        //ziskanie id stlaceneho tlacidla
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString((R.color.red)))));
        if(b.getInt("golden")!=1)
        {
        
        value = b.getInt("key");
        instance = b.getString("instance");
        
     // load the animation
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
         
        // set animation listener
        fadeIn.setAnimationListener(this);
        
        // load the animation
        rotation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
         
        // set animation listener
        rotation.setAnimationListener(this);
        
        setContentView(R.layout.activityfullscreen);
        
        tv = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        LinearLayout cont = (LinearLayout) findViewById(R.id.linearLayout1);
        tv2.setMovementMethod(new ScrollingMovementMethod());
        //rb = (RatingBar) findViewById(R.id.ratingBar1); 
        
        //zistenie ktore tlacidlo bolo stlacene
        for(int i=0;i<10;i++)
        {
    	   //finding the button and its rating
    	   if(value == Fragment_coupons.coupons[i].getImageBut())
    	   {
    		   //rb.setRating(Coupons_fragment.coupons[i].getRating());
    		   index = i;
    		   break;
    	   }
        }
        
       
       but = (Button) findViewById(R.id.button1);
       but.setBackgroundDrawable(getResources().getDrawable(R.drawable.coupon_use));
       setCoupon(index);
       
       but.setOnClickListener(new Button.OnClickListener(){

    	   @Override
    	   public void onClick(View arg0) {
    	    LayoutInflater layoutInflater 
    	     = (LayoutInflater)getBaseContext()
    	      .getSystemService(LAYOUT_INFLATER_SERVICE);  
    	    View popupView = layoutInflater.inflate(R.layout.popup_window, null);  
    	             final PopupWindow popupWindow = new PopupWindow(
    	               popupView, 
    	               LayoutParams.WRAP_CONTENT,  
    	                     LayoutParams.WRAP_CONTENT);  
    	             popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);  
    	             
    	             Button ano= (Button)popupView.findViewById(R.id.buttonano);
    	             Button nie = (Button)popupView.findViewById(R.id.buttonnie);
    	             //metoda na pridanie listenera na ano button
    	             //addListenerOnButton();
    	             ano.setOnClickListener(new View.OnClickListener() {
    	            	 @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    	            	 public void onClick(View arg0) {
    	            		 but.setVisibility(View.GONE);
    	            		 popupWindow.dismiss();
    	            		 getActionBar().hide();
    	            		 img.setAnimation(rotation);
    	            		 Fragment_coupons.coupons[index].setUsed(1);
    	            		 tv.setVisibility(View.GONE);
    	            		 tv2.setVisibility(View.GONE);
    	            		 db.CouponUsed(index+1);
    	            		 CouponSet.getCoupons().get(index).setUsed(1);
    	            		 
    	            		 //Coupons_fragment.coupons[index].setRating(rb.getRating());
    	            		 //ImageButton ib = (ImageButton)home.coupons[index].getImageBut();
    	            		 //rb.setVisibility(View.GONE);
    	            	 }
    	             });
    	             
    	             nie.setOnClickListener(new Button.OnClickListener(){
    	     @Override
    	     public void onClick(View v) {
    	      // TODO Auto-generated method stub
    	      popupWindow.dismiss();
    	     }});
    	               
    	             popupWindow.showAsDropDown(but, 50, -30);
    	         
    	   }});
       	    
       //addListenerOnRatingBar();
       }
        
       //if it is a golden coupon
    }
	//nadstavenie kuponu
	private void setCoupon(int couponNumb)
	{
		Coupon c = CouponSet.getCoupons().get(couponNumb);
		tv.setText(c.getTitle());
        tv2.setText(c.getAbout());
        img=(ImageButton)findViewById(R.id.picture);
        img.setBackgroundResource(R.drawable.couponimg);
        but.setText(c.getPrice());
	}
	/*
	public void addListenerOnRatingBar() {
	RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
	
	ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				Coupons_fragment.coupons[index].setRating(ratingBar.getRating());
			}
		});
	}
	*/
	
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		img.setBackgroundResource(R.drawable.barcode);
		img.setAnimation(fadeIn); 
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	}

