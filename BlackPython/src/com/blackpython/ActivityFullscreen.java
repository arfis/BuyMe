package com.blackpython;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;
import com.blackpython.R;

//aktivita pre zobrazovanie informacii o kuponov
public class ActivityFullscreen extends Activity {
	ImageView img;
	Index home;
	RatingBar rb;
	int value;
	int index;
	String instance;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        //ziskanie id stlaceneho tlacidla
        value = b.getInt("key");
        instance = b.getString("instance");
        
        setContentView(R.layout.activityfullscreen);
        
       rb = (RatingBar) findViewById(R.id.ratingBar1); 
       for(int i=0;i<10;i++)
       {
    	   //finding the button
    	   if(value == Index.coupons[i].getImageBut())
    	   {
    		   rb.setRating(Index.coupons[i].getRating());
    		   
    		   index = i;
    		   break;
    	   }
       }
       
       img=(ImageButton)findViewById(R.id.picture);
       img.setBackgroundResource(R.drawable.kupon);
       Toast.makeText(ActivityFullscreen.this, "toto bolo stlacene: " + 
       value, Toast.LENGTH_LONG).show();
       addListenerOnButton();
       addListenerOnRatingBar();
    }
	
	public void addListenerOnRatingBar() {
	RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
	
	ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener(){

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				Index.coupons[index].setRating(ratingBar.getRating());
			}
		});
	}
	
    public void addListenerOnButton() {
        ImageButton button;
   
             button=(ImageButton) findViewById(R.id.picture);	             
             button.setOnClickListener(new View.OnClickListener() {
            	 @Override
            	 public void onClick(View arg0) {
            		 img.setBackgroundResource(R.drawable.barcode);
            		 home.coupons[index].setUsed(1);
            		 home.coupons[index].setRating(rb.getRating());
            		 //ImageButton ib = (ImageButton)home.coupons[index].getImageBut();
            		 rb.setVisibility(View.GONE);
            	 }
             });
    }
}

