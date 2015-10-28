package com.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blackpython.R;
import data.Coupon;
import data.CouponSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.CouponsListAdapter;
import fakeData.FalseCoupons;
import data.DBCoupon;

public class Fragment_coupons extends Fragment{

	View v;
    private ArrayList<DBCoupon> AdataCoupons = new ArrayList<>();
    private ListView couponPage;
	int type;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_coupons, container, false);
		//adding the listener to the buttons - different style, cause it is in a fragment
	     couponPage = (ListView) v.findViewById(R.id.CouponList);
		Bundle bundle = this.getArguments();
		type = bundle.getInt("TYPE", -1);
		setCouponList(type);
		ImageView emptyView = new ImageView(this.getActivity().getApplicationContext());
		emptyView.setLayoutParams(new ViewGroup.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.FILL_PARENT));
		emptyView.setImageDrawable(this.getActivity().getApplicationContext().getResources().getDrawable(R.drawable.sold));
		emptyView.setVisibility(View.GONE);
		((ViewGroup)couponPage.getParent()).addView(emptyView);
		couponPage.setEmptyView(emptyView);

		return v;
    }
    public void setCouponList(int type)
    {
		//data ktore vstupuju do adaptera
    	if(AdataCoupons!=null && AdataCoupons.size()>0)AdataCoupons.clear();


		if(type == 1) {
			this.getActivity().setTitle("Hot Coupons");
			if(CouponSet.getCoupons() != null) {
				for (DBCoupon coupon : CouponSet.getCoupons()) {
					if (coupon.used == 0) {
						Activity activity = getActivity();
						AdataCoupons.add(coupon);

						CouponsListAdapter couponsList = new CouponsListAdapter(activity, AdataCoupons, getActivity());
						couponPage.setAdapter(couponsList);
					}
				}
			}
		}
		else {
			this.getActivity().setTitle("Golden Coupon");
			for (DBCoupon coupon : CouponSet.getGolden()) {
				if (coupon.used == 0) {
					Activity activity = getActivity();
					AdataCoupons.add(coupon);
					CouponsListAdapter couponsList = new CouponsListAdapter(activity, AdataCoupons,getActivity());
					couponPage.setAdapter(couponsList);
					//ak sa vyskytol zlaty kupon tak zobraz zlatu tehlicku
					getActivity().invalidateOptionsMenu();
				}
			}
		}
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
    	setCouponList(type);
    }
 
}
