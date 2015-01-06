package com.blackpython;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

//aktivita pre zobrazovanie informacii o kuponov
public class ActivityFullscreen extends Activity implements AnimationListener {
	ImageView img,bt;
	RatingBar rb;
	TextView tv,tv2;
	Button but,button;
	int value;
	int index;
	String instance;
	Animation rotation,fadeIn;
	boolean gold = false;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
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
        tv.setText("Vikendovy pobyt v hoteli Kempinsky Moskva");
        tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");
        LinearLayout cont = (LinearLayout) findViewById(R.id.linearLayout1);
        tv2.setMovementMethod(new ScrollingMovementMethod());
        
        //rb = (RatingBar) findViewById(R.id.ratingBar1); 
        
        //zistenie ktore tlacidlo bolo stlacene
        for(int i=0;i<10;i++)
        {
    	   //finding the button and its rating
    	   if(value == Coupons_fragment.coupons[i].getImageBut())
    	   {
    		   //rb.setRating(Coupons_fragment.coupons[i].getRating());
    		   index = i;
    		   break;
    	   }
        }
      
       img=(ImageButton)findViewById(R.id.picture);
       img.setBackgroundResource(R.drawable.couponimg);
       
       but = (Button) findViewById(R.id.button1);
       but.setBackgroundDrawable(getResources().getDrawable(R.drawable.couponbutton2));
       but.setText("200");
       
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
    	            	 @Override
    	            	 public void onClick(View arg0) {
    	            		 but.setVisibility(View.GONE);
    	            		 popupWindow.dismiss();
    	            		 getActionBar().hide();
    	            		 img.setAnimation(rotation);
    	            		 Coupons_fragment.coupons[index].setUsed(1);
    	            		 tv.setVisibility(View.GONE);
    	            		 tv2.setVisibility(View.GONE);
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

