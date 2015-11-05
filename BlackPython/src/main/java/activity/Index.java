package activity;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import async.LoadData;
import async.LoadData2;
import async.TaskCanceler;
import data.Const;
import data.CouponSet;
import data.DrawerItem;
import data.UserInformation;

import com.andexert.library.RippleView;
import com.facebook.Session;
import com.facebook.widget.ProfilePictureView;
import com.fragments.Fragment_coupons;
import com.blackpython.R;

import utils.FacebookPicture;
import utils.LoggingTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.Button;
import android.widget.ImageView;
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
    private TextView tcoupon,tinfo,tabout,tmap,trules;
    RippleView rw,rw2,rw3,rw4,rw5;
    ImageView logOut;
    Context context;

    ImageView googlePicture;
    FacebookPicture facebookPicture;


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

    private void goToLoginScreen()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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

        tcoupon = (TextView) findViewById(R.id.txtCoupon);
        tabout = (TextView) findViewById(R.id.txtAbout);
        tinfo = (TextView) findViewById(R.id.txtInfo);
        trules = (TextView) findViewById(R.id.txtRules);
        tmap = (TextView) findViewById(R.id.txtMap);

        logOut = (ImageView) findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int loggedMethod = UserInformation.getLoggedMethod();

                if (loggedMethod == LoggingTypes.GMAIL.getIntValue())
                {
                    GoogleConnection googleConnection = new GoogleConnection();
                    Bundle bundle = new Bundle();
                    bundle.putString("action", GoogleConnection.ACTION_DISCONNECT);
                    googleConnection.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content,googleConnection)
                            .commit();
                }
                else if (loggedMethod == LoggingTypes.FACEBOOK.getIntValue())
                {
                    callFacebookLogout(context);
                }

                UserInformation.clearUserData();
                goToLoginScreen();
            }
        });

        loadData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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


    }

    public void loadGoogleImage()
    {
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {

                try {
                    URL url = new URL(params[0]);
                    InputStream in = url.openStream();
                    return BitmapFactory.decodeStream(in);
                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                try
                {
                    googlePicture.setImageBitmap(bitmap);
                }
                catch (Exception e)
                {

                }
            }
        }.execute(UserInformation.getGoogleUserImage());

    }

    public void loadData()
    {
        googlePicture = (ImageView) findViewById(R.id.userPicture);
        facebookPicture = (FacebookPicture) findViewById(R.id.profilePictureView1);
        int loggedMethod = UserInformation.getLoggedMethod();

        if (loggedMethod == LoggingTypes.GMAIL.getIntValue())
        {
            googlePicture.setVisibility(View.VISIBLE);
            facebookPicture.setVisibility(View.GONE);
            loadGoogleImage();
        }
        else if (loggedMethod == LoggingTypes.FACEBOOK.getIntValue())
        {
            googlePicture.setVisibility(View.GONE);
            facebookPicture.setVisibility(View.VISIBLE);
            facebookPicture.setProfileId(UserInformation.getFacebookID());
        }
        else
        {
            googlePicture.setVisibility(View.GONE);
            facebookPicture.setVisibility(View.VISIBLE);
        }

        if (InternetValidation.haveNetworkConnection(this)) loadDatabase();

        //couponsCount.setText("Pocet pouzitych kuponov je: " + SharedPreferencesManager.getUsedCoupons());
        TextView dName = (TextView) findViewById(R.id.drawerName);
        dName.setText(UserInformation.getName());
    }

    public void updateCount(){
        //TextView couponsCount = (TextView) findViewById(R.id.usedCoupons);
        TextView newCoupons = (TextView) findViewById(R.id.counter);
        //couponsCount.setText("Pocet pouzitych kuponov je: " + SharedPreferencesManager.getUsedCoupons());
        if(SharedPreferencesManager.getNew()>0) {
            newCoupons.setVisibility(View.VISIBLE);
            newCoupons.setText(String.valueOf(SharedPreferencesManager.getNew()));
        }
        else {
            newCoupons.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.golden:
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
        resetDrawerColors();
        coupon.setBackgroundResource(R.color.greyLight);
        tcoupon.setTextColor(getResources().getColor(R.color.greyDark));
    }

    public void resetDrawerColors(){
        coupon.setBackgroundResource(R.color.greyDark);
        rules.setBackgroundResource(R.color.greyDark);
        about.setBackgroundResource(R.color.greyDark);
        info.setBackgroundResource(R.color.greyDark);
        map.setBackgroundResource(R.color.greyDark);
        tcoupon.setTextColor(getResources().getColor(R.color.greyLight));
        trules.setTextColor(getResources().getColor(R.color.greyLight));
        tabout.setTextColor(getResources().getColor(R.color.greyLight));
        tinfo.setTextColor(getResources().getColor(R.color.greyLight));
        tmap.setTextColor(getResources().getColor(R.color.greyLight));
    }

    public void showLayout(View view)
    {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        resetDrawerColors();

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
                tcoupon.setTextColor(getResources().getColor(R.color.greyDark));
            }

            });

        rw2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Intent intent = new Intent(Index.this, About_activity.class);
                intent.putExtra("Mode", "RULES");
                startActivity(intent);
                rules.setBackgroundResource(R.color.greyLight);
                trules.setTextColor(getResources().getColor(R.color.greyDark));
            }

        });
        rw3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Intent intent = new Intent(Index.this, About_activity.class);
                intent.putExtra("Mode","ABOUT");
                startActivity(intent);
                about.setBackgroundResource(R.color.greyLight);
                tabout.setTextColor(getResources().getColor(R.color.greyDark));
            }

        });
        rw4.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                Log.d("Sample", "Ripple completed");
                drawerLayout.closeDrawers();
                Intent intent = new Intent(Index.this, About_activity.class);
                intent.putExtra("Mode","INFO");
                startActivity(intent);
                info.setBackgroundResource(R.color.greyLight);
                tinfo.setTextColor(getResources().getColor(R.color.greyDark));
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
                tmap.setTextColor(getResources().getColor(R.color.greyDark));
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


    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem golden_icon = menu.findItem(R.id.golden);
        if(CouponSet.getGolden() == null || CouponSet.getGolden().size() == 0)
        {
            golden_icon.setVisible(false);
        }
        else
        {
            golden_icon.setVisible(true);
        }
        return true;
    }

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
        dialog.setCancelable(false);
        final Handler handler = new Handler();

        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                //LoadData ld = new LoadData(context);
                //ld.getData(context, Index.this);

                LoadData2 task = new LoadData2(context,Index.this);
                TaskCanceler taskCanceler = new TaskCanceler(task);
                handler.postDelayed(taskCanceler, 5*1000); //po piatich sekundach kill loadu
                task.execute();

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
                coupon.setBackgroundResource(R.color.greyLight);
                dialog.dismiss();

            }
        }.execute(context);


    }

}