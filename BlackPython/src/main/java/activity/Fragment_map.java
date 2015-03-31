package activity;

import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_map extends Activity{
	private GoogleMap map;
	
	  @SuppressLint("NewApi") @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString((R.color.red)))));
        
        
        
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment))
	        .getMap();
	    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
	    Criteria criteria = new Criteria();
	    String provider = service.getBestProvider(criteria, false);
	    Location location = service.getLastKnownLocation(provider);
	    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
	    CameraUpdate center=
	            CameraUpdateFactory.newLatLng(userLocation);
	        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
	        
	        map.moveCamera(center);
	        map.animateCamera(zoom);
	        map.addMarker(new MarkerOptions()
	          .position(userLocation));
	          
	  }

	private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
	    @Override
	    public void onMyLocationChange(Location location) {
	        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
	        Marker mMarker = map.addMarker(new MarkerOptions().position(loc));
	        if(map != null){
	            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
	        }
	    }
	};
}

