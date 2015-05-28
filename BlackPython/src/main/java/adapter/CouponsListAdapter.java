package adapter;

import java.util.ArrayList;

import activity.ActivityFullscreen;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackpython.R;
import data.Coupon;
import data.CouponSet;
import data.DrawerItem;
import data.UserInformation;

public class CouponsListAdapter extends ArrayAdapter<Coupon>{

	private static final int SET_COUPON_USED_FLAG = 0;
	Context mContext;
	ArrayList<Coupon> data;
	LayoutInflater inflater;
	Activity activity;

	 public CouponsListAdapter(Context mContext, ArrayList<Coupon> data,Activity activity){
		 
	        super(mContext, R.layout.component_coupons,data);
	        this.mContext = mContext;
	        this.data = data;
		 	this.activity = activity;
	        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 static class ViewHolder {
		    ImageView imageView;
		    }
	 
	 @Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Coupon getItem(int position) {
			return data.get(position);
		}
		
	    @SuppressLint("NewApi") @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	 
	        View listItem = convertView;
	        ViewHolder viewHolder;
	        
	        if (convertView == null) {
	            listItem = inflater.inflate(R.layout.component_coupons, null);
	            
	            viewHolder = new ViewHolder();
	            viewHolder.imageView = (ImageView) listItem.findViewById(R.id.couponView);
	            listItem.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) listItem.getTag();
	        }

	        
	        
	 
	        Coupon actualCoupon = data.get(position);
	        viewHolder.imageView.setTag(actualCoupon.getId());

	        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {

				@Override
	            public void onClick(View v) {
	            	Intent intent = new Intent(mContext, ActivityFullscreen.class);
	                Bundle b = new Bundle();
	                //do bundle sa vlozi hodnota id taka ista ako je v databaze
	                Log.i("Coupon","Selected coupon is: " + v.getTag());
					b.putInt("pressed_coupon", (Integer) v.getTag());
					Log.i("clicked", v.getTag().toString());
	        		intent.putExtras(b); //Put your id to your next Intent
	        		mContext.startActivity(intent);
					activity.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
	            }
	        });

	        Bitmap blob = data.get(position).getPicture();
	        
	        viewHolder.imageView.setImageBitmap(blob);
	       
	        return listItem;
	    }
	 
	    
	    @Override
	    public int getViewTypeCount() {
	        return 2;
	    }

	    @Override
	    public int getItemViewType(int position) {
	        if(position%2==0) return 0;
	        return 1;
	    } 
	 
}
