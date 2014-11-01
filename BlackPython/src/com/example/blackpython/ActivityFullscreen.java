package com.example.blackpython;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.blackpython.R;

//aktivita pre zobrazovanie informacii o kuponov
public class ActivityFullscreen extends Activity {
	ImageView img;
	int value;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        //ziskanie id stlaceneho tlacidla
        value = b.getInt("key");
        setContentView(R.layout.activityfullscreen);
       img=(ImageButton)findViewById(R.id.picture);
       img.setBackgroundResource(R.drawable.kupon);
       Toast.makeText(ActivityFullscreen.this, "toto bolo stlacene: " + 
       value, Toast.LENGTH_LONG).show();
       addListenerOnButton();
    }
    
    public void addListenerOnButton() {
        ImageButton button;
   
             button=(ImageButton) findViewById(R.id.picture);	             
             button.setOnClickListener(new View.OnClickListener() {
            	 @Override
            	 public void onClick(View arg0) {
            		 img.setBackgroundResource(R.drawable.barcode);

            	 }
             });
    }
}

