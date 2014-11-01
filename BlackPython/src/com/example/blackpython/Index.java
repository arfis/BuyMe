package com.example.blackpython;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.data.Coupon;
import com.data.MemoryStorage;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Build;
import android.preference.PreferenceManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Index extends ActionBarActivity {
	
	boolean firstTime = true;
	int pressed;
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
	MemoryStorage coup;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getIntent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.d("CREATE","nastal create");
        setContentView(R.layout.activity_index);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .commit();            
        }
        if(firstTime == true)
        {
        MemoryStorage coup = new MemoryStorage(this);
        coup.addCoupon(new Coupon("10% Zlava na arasidy",0,1));
        coup.addCoupon(new Coupon("20% zlava na feferony",0,1));
        Log.d("DB", Integer.toString(coup.getAllCoupons().size()));
        new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
        addListenerOnButton(); 
        firstTime = false;
        
        }
    }
	public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	   private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        Log.d("json",result);
	        return result;
	 
	    }
	 
	    public boolean isConnected(){
	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            if (networkInfo != null && networkInfo.isConnected()) 
	                return true;
	            else
	                return false;   
	    }
	    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	 
	            return GET(urls[0]);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
	       }
	    }
	
    @Override
    public void onPause()
    {
    	super.onPause();
    	//this.getIntent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    }
    @Override
    public void onResume()
    {
    	super.onResume();
    	Log.d("RESUME","nastal resume");  
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }
    
    public void addListenerOnButton() {
        ImageButton imagebuttons[]={ button1,button2,button3,button4,button5,
        		button6,button7,button8,button9,button10};
        
    int ids[]={R.id.ImageButton1,R.id.ImageButton2,R.id.ImageButton3,R.id.ImageButton4,R.id.ImageButton5
    		,R.id.ImageButton6,R.id.ImageButton7,R.id.ImageButton8,R.id.ImageButton9
    		,R.id.ImageButton10};
    
    	 for(int i=0;i<imagebuttons.length;i++)
         {
             imagebuttons[i]=(ImageButton) findViewById(ids[i]);	            	
             imagebuttons[i].setOnClickListener(new View.OnClickListener() {
            	 @Override
			public void onClick(View arg0) {
               Intent intent = new Intent(Index.this, ActivityFullscreen.class);
			   Bundle b = new Bundle();
			   
			   b.putInt("key", arg0.getId());
			   pressed = arg0.getId();
			   intent.putExtras(b); //Put your id to your next Intent
			   startActivity(intent);
			}
             });
		}
 
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
