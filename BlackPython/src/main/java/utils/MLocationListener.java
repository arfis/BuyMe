package utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.blackpython.R;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MLocationListener implements LocationListener {

	private static final String TAG = "GEO Listener";
	Context context;
	View view;
	TextView mainText;
	
	public MLocationListener(Context context,View v)
	{
		this.context = context;
		this.view = view;
	}
	
	
	    @Override
	    public void onLocationChanged(Location loc) {
	   
	        String longitude = "Longitude: " + loc.getLongitude();
	        Log.v(TAG, longitude);
	        String latitude = "Latitude: " + loc.getLatitude();
	        Log.v(TAG, latitude);

	        /*------- To get city name from coordinates -------- */
	        String cityName = null;
	        Geocoder gcd = new Geocoder(context, Locale.getDefault());
	        List<Address> addresses;
	        try {
	            addresses = gcd.getFromLocation(loc.getLatitude(),
	                    loc.getLongitude(), 1);
	            if (addresses.size() > 0)
	                System.out.println(addresses.get(0).getLocality());
	            cityName = addresses.get(0).getLocality();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
	            + cityName;
	        
	        mainText.setText(cityName + " " + latitude + " " + longitude);
	        
	        
	        Log.d(TAG,cityName);
	      
	    }

	    @Override
	    public void onProviderDisabled(String provider) {}

	    @Override
	    public void onProviderEnabled(String provider) {}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

	 
	}

