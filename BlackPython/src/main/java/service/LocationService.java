package service;

import org.osmdroid.util.GeoPoint;

import com.blackpython.R;
import com.utils.GeoMath;

import activity.Index;
import activity.Map;
import fakeData.FalsePoints;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service{

	public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 100 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;
    int mNumber = 0;
    String currentPoint = "";

    Intent intent;
    int counter = 0;

 @Override
 public void onCreate() 
 {
     super.onCreate();
     Toast.makeText(this, "Background Service Created", Toast.LENGTH_LONG).show();
     intent = new Intent(BROADCAST_ACTION);      
 }


 @Override
 public int onStartCommand(Intent intent, int flags, int startId) 
 {
     Log.i("Service","spustil sa service asi");
     locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     listener = new MyLocationListener();        
     locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);

     String locationProvider = LocationManager.NETWORK_PROVIDER;
// Or use LocationManager.GPS_PROVIDER

     Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
     locationProvider = LocationManager.GPS_PROVIDER;
     //showNotification("co to je notice", "TITLEEEEE nieco sa vyhodilo", "sprava je blablabla");
 
 return START_STICKY;
 }

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

 protected boolean isBetterLocation(Location location, Location currentBestLocation) {
     if (currentBestLocation == null) {
         // A new location is always better than no location
         return true;
     }

     // Check whether the new location fix is newer or older
     long timeDelta = location.getTime() - currentBestLocation.getTime();
     boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
     boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
     boolean isNewer = timeDelta > 0;

     // If it's been more than two minutes since the current location, use the new location
     // because the user has likely moved
     if (isSignificantlyNewer) {
         return true;
     // If the new location is more than two minutes older, it must be worse
     } else if (isSignificantlyOlder) {
         return false;
     }

     // Check whether the new location fix is more or less accurate
     int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
     boolean isLessAccurate = accuracyDelta > 0;
     boolean isMoreAccurate = accuracyDelta < 0;
     boolean isSignificantlyLessAccurate = accuracyDelta > 200;

     // Check if the old and new location are from the same provider
     boolean isFromSameProvider = isSameProvider(location.getProvider(),
             currentBestLocation.getProvider());

     // Determine location quality using a combination of timeliness and accuracy
     if (isMoreAccurate) {
         return true;
     } else if (isNewer && !isLessAccurate) {
         return true;
     } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
         return true;
     }
     return false;
 }



/** Checks whether two providers are the same */
 private boolean isSameProvider(String provider1, String provider2) {
     if (provider1 == null) {
       return provider2 == null;
     }
     return provider1.equals(provider2);
 }



@Override
 public void onDestroy() {       
    // handler.removeCallbacks(sendUpdatesToUI);     
     super.onDestroy();
     Log.v("STOP_SERVICE", "DONE");
     locationManager.removeUpdates(listener);        
 }   


public class MyLocationListener implements LocationListener
 {

     public void onLocationChanged(final Location loc)
     {
         Log.i("das", "Location changed");

        //vytvorenie geoPointu
         int lat = (int) (loc.getLatitude() * 1E6);
         int lng = (int) (loc.getLongitude() * 1E6);
         GeoPoint point = new GeoPoint(lat, lng);

         //zobrazenie pozicie na mape
         Map.changePosition(point);

         isNear(point);
         if(isBetterLocation(loc, previousBestLocation)) {
             loc.getLatitude();
             loc.getLongitude();             
             intent.putExtra("Latitude", loc.getLatitude());
             intent.putExtra("Longitude", loc.getLongitude());     
             intent.putExtra("Provider", loc.getProvider());                 
             sendBroadcast(intent);
         }

     }

     public GeoPoint isNear(GeoPoint myLocation){

         for(int i =0;i< FalsePoints.data.size();i++) {
             //Ak je vzdialenost medzi bodmi menej ako
             Log.i("Notifikacia","Vzdialenost je: " + GeoMath.getDistance(myLocation, FalsePoints.data.get(i).getGeoPoint()));
             if(GeoMath.getDistance(myLocation, FalsePoints.data.get(i).getGeoPoint()) < 100
                     && !FalsePoints.data.get(i).name.equals(currentPoint)){

                 Log.i("Notifikacia","Vzdialenost je: " + GeoMath.getDistance(myLocation, FalsePoints.data.get(i).getGeoPoint()));
                 currentPoint = FalsePoints.data.get(i).name;
                 Log.i("Notifikacia",currentPoint);
                 showNotification(currentPoint, "nastala zmena", "hybeme sa :P");
                 return myLocation;
             }
         }
         return null;

     }

     public void onProviderDisabled(String provider)
     {
         Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
     }


     public void onProviderEnabled(String provider)
     {
         Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
     }


     public void onStatusChanged(String provider, int status, Bundle extras)
     {

     }

 }

@Override
public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
}
}
