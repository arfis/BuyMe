package activity;

import java.util.List;
import java.util.ResourceBundle;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import data.Coupon;
import data.CouponSet;
import data.MemoryStorage;
import data.UserInformation;
import com.fragments.Fragment_coupons;

import manager.SharedPreferencesManager;

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
	int FLAG_SET_USED = 0;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        db = UserInformation.getMemory();
        
        value = b.getInt("pressed_coupon");
        //instance = b.getString("instance");
        
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
       tv2.setMovementMethod(new ScrollingMovementMethod());
        
       
       but = (Button) findViewById(R.id.addpoint);
       but.setBackgroundDrawable(getResources().getDrawable(R.drawable.coupon_use));
       
       //nadstavenie informacii o kupone na screen
       setCouponInformation(value);
       
       but.setOnClickListener(new Button.OnClickListener(){

    	   @Override
    	   public void onClick(View arg0) {
    	    LayoutInflater layoutInflater 
    	     = (LayoutInflater)getBaseContext()
    	      .getSystemService(LAYOUT_INFLATER_SERVICE);  
    	    //popup window po kliknuti na pouzitie kuponu
    	    View popupView = layoutInflater.inflate(R.layout.popup_window, null);

    	             final PopupWindow popupWindow = new PopupWindow(
    	               popupView, 
    	               LayoutParams.WRAP_CONTENT,  
    	                     LayoutParams.WRAP_CONTENT);  
    	             //nastavenie do stredu obrazovky
    	             
    	             popupWindow.showAtLocation(arg0, Gravity.CENTER, 0, 0);

    	             ImageView image = (ImageView)popupView.findViewById(R.id.pop_image);
    	             Button ano= (Button)popupView.findViewById(R.id.buttonano);
    	             Button nie = (Button)popupView.findViewById(R.id.buttonnie);
    	             image.setBackgroundResource(R.drawable.used);
    	             
    	             //metoda na pridanie listenera na ano button
    	             //addListenerOnButton();
    	             ano.setOnClickListener(new View.OnClickListener() {
    	            	 @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
    	            	 @Override
    	            	 public void onClick(View arg0) {
    	            		 but.setVisibility(View.GONE);
    	            		 popupWindow.dismiss();
    	            		 //getActionBar().hide();
    	            		 img.setAnimation(rotation);
    	            		 tv.setVisibility(View.GONE);
    	            		 tv2.setVisibility(View.GONE);
    	            		 Log.d("USED", "used index: " + value);
							 removeCoupon(value);
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
    }

	private void removeCoupon(int value){

		SharedPreferencesManager.setUsedCoupon();
		db.CouponUsed(value);

		for(int i =0;i<CouponSet.getCoupons().size();i++) {
			if(CouponSet.getCoupons().get(i).getId() == value) {
				CouponSet.getCoupons().get(i).setUsed(1);
			}
		}
	}

	//nadstavenie informacii o kupone
	private void setCouponInformation(int couponNumb)
	{
		int i = 0;
		Coupon c = null;
		while(CouponSet.getCoupons().get(i)!=null) {
			if(CouponSet.getCoupons().get(i).getId()==couponNumb) {
				c = CouponSet.getCoupons().get(i);
				break;
			}
			i++;
		}

		Log.d("USED COUPON","cislo used je: " + c.isUsed());
		tv.setText(c.getTitle());
        tv2.setText(c.getAbout());
        img=(ImageButton)findViewById(R.id.picture);
        img.setBackgroundResource(R.drawable.couponimg);
        but.setText(c.getPrice());
	}
	
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

