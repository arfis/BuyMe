package activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

        Button btn_notify = (Button) findViewById(R.id.btn_notify);

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification("Notifikacia","Nakupne stredisko Billa","Nachadzate sa v blizkosti nakupneho strediska Billa :)");
            }
        });

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

    int mNumber = 0;
    private void showNotification(String notice, String title, String message) {

        String TAG = "notification";
        Log.i("Start", "notification");

        Intent notificationIntent = new Intent(this, Map.class);
        notificationIntent.putExtra(TAG,true);


        PendingIntent contentIntent = PendingIntent.getActivity(this,
                9999, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nm = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = this.getResources();
        Notification.Builder builder = new Notification.Builder(this);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //res.getString(R.string.your_ticker
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setTicker(title)
                .setNumber(++mNumber)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(notice)
                .setSound(alarmSound)
                .setContentText(message);
        Notification n = builder.getNotification();

        nm.notify(9999, n);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
