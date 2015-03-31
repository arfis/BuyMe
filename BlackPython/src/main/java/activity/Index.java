package activity;

import java.util.ArrayList;
import com.data.CouponSet;
import com.data.DrawerItem;
import com.data.MemoryStorage;
import com.data.UserInformation;
import com.facebook.widget.ProfilePictureView;
import com.fragments.Fragment_about;
import com.fragments.Fragment_coupons;
import com.fragments.Fragment_info;
import com.fragments.Fragment_profil;
import com.fragments.Fragment_rulez;
import com.gui.ButtonCoupon;
import com.blackpython.R;
import com.utils.LoggingTypes;

import adapter.DrawerListAdapter;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fakeData.FalsePoints;
import manager.SharedPreferencesManager;
import service.LocationService;
import fakeData.FalseCoupons;

public class Index extends  FragmentActivity {
	
	private final static String TAG_FRAGMENT = "INDEX";
	boolean firstTime = true;
	int pressed;
	int unUsed;
	int test;
	String databaseLink = "http://192.168.1.11/test.html";
	static ButtonCoupon[] coupons = new ButtonCoupon[10];
	String mTitle = "zatvorene";
	String mDrawerTitle = "otvorene?";
	//MemoryStorage coup;
	//pocet prvkov v drawer-i
	ArrayList<DrawerItem> data = new ArrayList();
	private DrawerLayout drawerLayout;
	private String[] drawerListViewItems;
    private ListView drawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    Context context;

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.index, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        loadDatabase();
        new FalsePoints();
        //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        startService(new Intent(this.getApplicationContext(), LocationService.class));
        SharedPreferencesManager.init(getApplicationContext());
    //    if (savedInstanceState == null) {

        setContentView(R.layout.activity_index);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString((R.color.red)))));
        //potialto, posledny riadok je len pre novu api ice cream sandwich
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this,
        		drawerLayout,
        		R.drawable.ic_drawer, //pridanie novej ikonky
        		R.string.drawer_open,
        		R.string.drawer_close){

        /** Called when drawer is closed */
        public void onDrawerClosed(View view) {
        //highlightSelectedCountry();
        supportInvalidateOptionsMenu();
        }

       /** Called when a drawer is opened */
        public void onDrawerOpened(View drawerView) {
        //getSupportActionBar().setTitle("Select a Country");
        supportInvalidateOptionsMenu();
        }
        };
        //adding data to the coupon structure
        drawDrawer();
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }
	
	 public void drawDrawer()
	    {
		  String[] mTestArray =   getResources().getStringArray(R.array.items);  
		  	if(data.size()==0)
		  	{
	        //pridanie poloziek do laveho vysuvacieho menu
		   	data.add(new DrawerItem(R.drawable.coup,mTestArray[0],unUsed));
	        data.add(new DrawerItem(R.drawable.rulez,mTestArray[1],0));
	        data.add(new DrawerItem(R.drawable.about,mTestArray[2],0));
	        data.add(new DrawerItem(R.drawable.info,mTestArray[3],0));
	        data.add(new DrawerItem(R.drawable.geo,mTestArray[4],0));
		  	}
	        
	        DrawerListAdapter adapter = new DrawerListAdapter(this, R.layout.drawerlist_row, data);
	        // Setting event listener for the drawer
	        drawerLayout.setDrawerListener(mDrawerToggle);
	        // get ListView defined in activity_main.xml
	        drawerListView = (ListView) findViewById(R.id.left_drawer);
	        // Set the adapter for the list view
	        drawerListView.setAdapter(adapter);

            ProfilePictureView pf = (ProfilePictureView) findViewById(R.id.profilePictureView1);

            if(SharedPreferencesManager.getLoggedMethod() == LoggingTypes.FREE.getIntValue()) {
                pf.setVisibility(View.GONE);
            }
            else
                pf.setProfileId(UserInformation.getId());

            TextView couponsCount = (TextView) findViewById(R.id.usedCoupons);
            couponsCount.setText("Pocet pouzitych kuponov je: " + SharedPreferencesManager.getUsedCoupons());
	        TextView dName = (TextView) findViewById(R.id.drawerName);
	        dName.setText(UserInformation.getName());
	    }
    public void updateCount(){
        TextView couponsCount = (TextView) findViewById(R.id.usedCoupons);
        couponsCount.setText("Pocet pouzitych kuponov je: " + SharedPreferencesManager.getUsedCoupons());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.action_cart:
                Toast.makeText(this, "klikol si na ikonku hore", Toast.LENGTH_SHORT)
                        .show();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment_coupons coup = new Fragment_coupons();
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 2);
                coup.setArguments(bundle);
                ft.replace(R.id.frame_container, coup, TAG_FRAGMENT);
                ft.addToBackStack(null);
                ft.commit();
                break;
            default:
                break;
        }
        return true;
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
        updateCount();

    }
    
    public void onStop()
    {
    	super.onStop();
    	//UserInformation.getMemory().close();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    //adding listeners to the coupons - every image button has got a unique listener
    
    //If there is an icon
    
    public void loadDatabase()
    {
        final ProgressDialog dialog = new ProgressDialog(Index.this);
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                //TODO: vytvorenie connection na api pre ziskanie a taktiez ziskanie timestampu ktory sa
                MemoryStorage coup = new MemoryStorage(contexts[0]);
                Log.d("kurva","koniec prvej casti");
                UserInformation.setMemory(coup);
                Log.d("kurva", "koniec druhej casti");
                FalseCoupons.setDatabase(contexts[0]);
                Log.d("kurva", "koniec tretej casti");
                CouponSet.setCoupons(coup.getAllCoupons());
                Log.d("kurva", "koniec stvrtej casti");
                FalseCoupons.setGolden(getApplicationContext());

               return null;
            }

            protected void onPreExecute(){
                //dialog.dismiss();
                super.onPreExecute();
                dialog.setMessage("prebieha nacitanie databazy");
                dialog.show();
                Log.d("kurva","zobrazenie");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("kurva","po post execute");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment_coupons coup = new Fragment_coupons();
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 1);
                coup.setArguments(bundle);
                ft.replace(R.id.frame_container, coup);
                ft.commit();

                dialog.dismiss();

            }
        }.execute(context);


    }
        
    //po kliknuti na item z drawera
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        private static final String TAG_FRAGMENT = "index";

		@Override
        public void onItemClick(AdapterView parent, View view, int position,long id) {
           // Highlight the selected item, update the title, and close the drawer
           // update selected item and title, then close the drawer
           drawerListView.setItemChecked(position, true);
            drawerLayout.closeDrawers();
            //drawerLayout.closeDrawer(Gravity.NO_GRAVITY);
            setTitle("Hot Coupons");

           showLayout(position);

        }
        //zobrazenie noveho fragmentu
        void showLayout(int position)
        {
        	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        	
        	switch(position){
        	case 0:
            	Fragment_coupons coup = new Fragment_coupons();
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 1);
                coup.setArguments(bundle);
            	ft.replace(R.id.frame_container, coup, TAG_FRAGMENT);
            	ft.addToBackStack(null);
            	ft.commit();
                break;
        	case 1:
        		Fragment_rulez rules = new Fragment_rulez();
                ft.replace(R.id.frame_container, rules);
                ft.addToBackStack(null);
                ft.commit();
                break;
        	case 2:
                Fragment_about about = new Fragment_about();
                ft.replace(R.id.frame_container, about);
                ft.addToBackStack(null);
                ft.commit();
                break;
        	case 3:
        		Fragment_info info = new Fragment_info();
                ft.replace(R.id.frame_container, info);
                ft.addToBackStack(null);
                ft.commit();
                break;
        	case 4:
        		Intent intent = new Intent(Index.this, Map.class);
				startActivity(intent);
                break;
        	case 5:
        		Fragment_profil profil = new Fragment_profil();
                ft.replace(R.id.frame_container, profil);
                ft.addToBackStack(null);
                ft.commit();
        } 
        }
    }   
}