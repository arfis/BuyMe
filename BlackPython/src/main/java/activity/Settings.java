package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.blackpython.R;

import manager.SharedPreferencesManager;
import service.LocationService;

/**
 * Created by Snow on 5/27/2015.
 */
public class Settings extends ActionBarActivity {
    Context context;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this.getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Switch location = (Switch) findViewById(R.id.switch_location);
        location.setChecked(SharedPreferencesManager.getLocationSetting());

        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesManager.setLocationSetting(b);
                Toast.makeText(context, "boolean hodnota je: " + b, Toast.LENGTH_SHORT).show();
                if (b) {
                    startService(new Intent(context, LocationService.class));
                } else {
                    stopService(new Intent(context, LocationService.class));
                    Toast.makeText(context, "Vypnutie service-u" + b, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
