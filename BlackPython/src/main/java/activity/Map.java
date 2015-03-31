package activity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.blackpython.R;
import com.data.Places;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.utils.GeoMath;
import com.utils.MLocationListener;

import fakeData.FalsePoints;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.GeometryMath;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import manager.GPSManager;
import service.LocationService;

import static android.widget.Toast.makeText;


public class Map extends Activity {

	private static final long MIN_TIME_BW_UPDATES = 0;

	private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

	View v;
	
	   	private MapView  mMapView;
	    static private MapController   mMapController;
	    private LayoutInflater 	inflater;
	    private TextView 		tMap;
	    private Location 		location;
	    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
	    private ResourceProxy 	mResourceProxy;
	    double 					latitude, longitude;
	    static GeoPoint 				myLocation;
	    List<Overlay> 			mOverlays;
	    ArrayList<OverlayItem> 	items;
		GoogleApiClient mGoogleApiClient;
		ProgressDialog dialog;

	private boolean canGetLocation;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fragment_freemap);

			inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
	        v = inflater.inflate(R.layout.fragment_freemap, null);

	        mResourceProxy = new ResourceProxyImpl(getApplicationContext());

	        mMapView = (MapView) findViewById(R.id.mapview);
	        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
	        mMapView.setBuiltInZoomControls(true);

	        mMapController = (MapController) mMapView.getController();
	        mMapController.setZoom(20);
	        
	        tMap = (TextView) findViewById(R.id.mapTextView);

			if(myLocation == null) {

				makeText(getApplicationContext(),"nebola najdena pozicia", Toast.LENGTH_SHORT).show();
				finish();
			}
			else {
				tMap.setText(myLocation.getLatitude() + " " + myLocation.getLongitude());
				mMapController.setCenter(myLocation);

				items = new ArrayList<OverlayItem>();
				fillMapData();
				setIconsOnMap();
			}
				Intent intent = new Intent(this, LocationService.class);
				startService(intent);

	       
	    }
	    private void setIconsOnMap()
	    {
	    	//nadstavenie novych mapovych markerov
	    	Drawable newMarker = this.getResources().getDrawable(R.drawable.geo);
	    	
	    	mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,newMarker,
	                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
	        	
	                    @Override
	                    public boolean onItemSingleTapUp(final int index,
	                            final OverlayItem item) {
	                    		
	                    	double distInMeters = GeoMath.getDistance(myLocation, item.getPoint());
	                    		
	                    	if(distInMeters<100)
	                    	{
	                        makeText(
									Map.this,
									"Odstranenie bodu: " + item.getTitle(), Toast.LENGTH_LONG).show();
	                        removeFromList(index);
	                        mMapView.refreshDrawableState();
	                        mMapView.invalidate();
	                    	}
	                    	else
	                    	{
	                    		makeText(
										Map.this,
										"Si ďaleko od zvoleného bodu a to: " + distInMeters + " m"
										, Toast.LENGTH_LONG).show();
	                    	}
	                        return true; // We 'handled' this event.
	                    }
	                    
	                    @Override
	                    public boolean onItemLongPress(final int index,
	                            final OverlayItem item) {
	                    	double distInMeters = GeoMath.getDistance(myLocation, item.getPoint());
	                        makeText(
									Map.this,
									"Dany bod je od teba vzdialeny: " + distInMeters + " m"
									, Toast.LENGTH_LONG).show();
	                        return false;
	                    }
					}, mResourceProxy);
	        
	        this.mMapView.getOverlays().add(this.mMyLocationOverlay);
	        mMapView.invalidate();     
	        mMapView.refreshDrawableState();
	    }

	static public void changePosition(GeoPoint loc){

		if(mMapController != null) mMapController.setCenter(loc);

		myLocation = loc;
	}
	    private void addPointToList()
	    {
	    	mOverlays=mMapView.getOverlays();
	    	items.add(new OverlayItem("Here", "SampleDescription", new GeoPoint(myLocation)));
	    	mMapView.invalidate();     
	        mMapView.refreshDrawableState();
	    }
	    
	    private void removeFromList(int index)
	    {
	    	items.remove(index);
	    	mMyLocationOverlay.getItem(index).setMarker(this.getResources().getDrawable(R.drawable.abc_ab_share_pack_holo_dark));
	    	mMapView.invalidate();     
	        mMapView.refreshDrawableState();
	    }


	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
	}

	private void fillMapData()
	    {
	    	if(items.size()==0)
	    	{

	  	        List<Places> mData = FalsePoints.data;
	  	        
	  	        for(Places place : mData)
	  	        {
	  	        	items.add(new OverlayItem(place.name, place.about, place.getGeoPoint()));	
	  	        }
	    	}
	    }

}
