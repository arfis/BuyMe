package com.blackpython;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.data.Coupon;
import com.data.MemoryStorage;
import com.gui.ButtonCoupon;
import com.blackpython.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Index extends  FragmentActivity {
	
	boolean firstTime = true;
	int pressed;
	static ButtonCoupon[] coupons = new ButtonCoupon[10];
	
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
	private DrawerLayout drawerLayout;
	private String[] drawerListViewItems;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.index, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getIntent().setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.blackpython", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("SHA");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e){e.printStackTrace();};
        
        Log.d("CREATE","nastal create");
        setContentView(R.layout.activity_index);
        if (savedInstanceState == null) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Coupons_fragment coup = new Coupons_fragment();
        ft.replace(R.id.frame_container, coup);
        ft.commit();
        }
        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.items);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);
 
                // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_photo, drawerListViewItems));
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));
         
        MemoryStorage coup = new MemoryStorage(this);
        coup.addCoupon(new Coupon("10% Zlava na arasidy",0,1));
        coup.addCoupon(new Coupon("20% zlava na feferony",0,1));
        Log.d("DB", Integer.toString(coup.getAllCoupons().size()));
        new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
        firstTime = false;
        //adding data to the coupon structure
        
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
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
	            Log.d("get","this is the result:" + result);
	        }
	    }
	
    @Override
    public void onPause()
    {
    	super.onPause();	
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    
    //adding listeners to the coupons - every image button has got a unique listener
    
    //If there is an icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
         // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event
 
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView parent, View view, int position,long id) {
          
           // Highlight the selected item, update the title, and close the drawer
           // update selected item and title, then close the drawer
           drawerListView.setItemChecked(position, true);
           setTitle("......");

           String text= "menu click... should be implemented: " + position;
           Toast.makeText(Index.this, text , Toast.LENGTH_LONG).show();
           showLayout(position);
           drawerLayout.closeDrawer(drawerListView);
           
        }
        void showLayout(int position)
        {
        	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        	
        	switch(position){
        	case 0:
            	Coupons_fragment coup = new Coupons_fragment();
            	ft.replace(R.id.frame_container, coup);
            	ft.addToBackStack(null);
            	ft.commit();
            	//addListenerOnButton();
            	//setButtons();
                break;
        	case 1:
        		Rules_fragment rules = new Rules_fragment();
                ft.replace(R.id.frame_container, rules);
                ft.addToBackStack(null);
                ft.commit();
                break;
        	case 2:
                About_fragment about = new About_fragment();
                ft.replace(R.id.frame_container, about);
                ft.addToBackStack(null);
                ft.commit();
                break;
        	case 3:
        		Intent intent = new Intent(Index.this, ActivityFullscreen.class);
                Bundle b = new Bundle();	   
                b.putInt("golden", 1);
        		intent.putExtras(b); //Put your id to your next Intent
        		startActivity(intent);
        		
        } 
        }
    }   
}