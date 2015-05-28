package activity;

import java.util.ArrayList;

import async.LoadData;
import data.Const;
import data.DrawerItem;
import data.UserInformation;

import com.andexert.library.RippleView;
import com.facebook.widget.ProfilePictureView;
import com.fragments.Fragment_about;
import com.fragments.Fragment_coupons;
import com.fragments.Fragment_info;
import com.fragments.Fragment_profil;
import com.fragments.Fragment_rulez;
import com.blackpython.R;
import utils.LoggingTypes;

import adapter.DrawerListAdapter;

import android.app.LauncherActivity;
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
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import fakeData.FalsePoints;
import manager.SharedPreferencesManager;
import service.LocationService;

public class Index extends ActionBarActivity {

    private final static String TAG_FRAGMENT = "INDEX";
    ArrayList<DrawerItem> data = new ArrayList();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TableRow coupon, info, about, map, rules;
    RippleView rw,rw2,rw3,rw4,rw5;
    Context context;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.index, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void refresh(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment_coupons coup = new Fragment_coupons();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", 1);
        coup.setArguments(bundle);
        ft.replace(R.id.frame_container, coup);
        ft.commit();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Const.IMEI = telephonyManager.getDeviceId();

        setContentView(R.layout.activity_index);

        rw = (RippleView) findViewById(R.id.more);
        rw2 = (RippleView) findViewById(R.id.more2);
        rw3 = (RippleView) findViewById(R.id.more3);
        rw4 = (RippleView) findViewById(R.id.more5);
        rw5 = (RippleView) findViewById(R.id.more6);

        coupon = (TableRow) findViewById(R.id.coupon);
        about = (TableRow) findViewById(R.id.about);
        info = (TableRow) findViewById(R.id.info);
        rules = (TableRow) findViewById(R.id.rulez);
        map = (TableRow) findViewById(R.id.map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        loadDatabase();
        SharedPreferencesManager.init(getApplicationContext());

        new FalsePoints();
        if(SharedPreferencesManager.getLocationSetting()) {
            startService(new Intent(this.getApplicationContext(), LocationService.class));
        }

        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,0,0);
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        drawDrawer();
    }


    public void drawDrawer()
    {

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
                showGolden();
                break;
            case R.id.settings:
                showSettings();
                break;
            default:
                break;
        }
        return true;
    }

    public void showSettings(){
        Intent settings = new Intent(context,Settings.class);
        startActivity(settings);
    }

    public void showGolden(){
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
        loadDatabase();
    }
    public void showLayout(View view)
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        coupon.setBackgroundResource(R.color.greyDark);
        rules.setBackgroundResource(R.color.greyDark);
        about.setBackgroundResource(R.color.greyDark);
        info.setBackgroundResource(R.color.greyDark);
        map.setBackgroundResource(R.color.greyDark);

        rw.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Fragment_coupons coup = new Fragment_coupons();
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE", 1);
                coup.setArguments(bundle);
                ft.replace(R.id.frame_container, coup, TAG_FRAGMENT);
                ft.addToBackStack(null);
                ft.commit();
                coupon.setBackgroundResource(R.color.greyLight);
            }

            });

        rw2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Fragment_rulez frules = new Fragment_rulez();
                ft.replace(R.id.frame_container, frules);
                ft.addToBackStack(null);
                ft.commit();
                rules.setBackgroundResource(R.color.greyLight);
            }

        });
        rw3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Fragment_about fabout = new Fragment_about();
                ft.replace(R.id.frame_container, fabout);
                ft.addToBackStack(null);
                ft.commit();
                about.setBackgroundResource(R.color.greyLight);
            }

        });
        rw4.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Fragment_info finfo = new Fragment_info();
                ft.replace(R.id.frame_container, finfo);
                ft.addToBackStack(null);
                ft.commit();
                info.setBackgroundResource(R.color.greyLight);
            }

        });
        rw5.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Intent intent = new Intent(Index.this, Map.class);
                startActivity(intent);
                map.setBackgroundResource(R.color.greyLight);
            }

        });

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
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START| Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    public void loadDatabase()
    {
        final ProgressDialog dialog = new ProgressDialog(Index.this);

        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                LoadData ld = new LoadData(context);
                ld.getData(context, Index.this);

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

}