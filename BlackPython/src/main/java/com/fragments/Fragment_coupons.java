package com.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.blackpython.R;
import com.data.Coupon;
import com.data.CouponSet;
import java.util.ArrayList;
import adapter.CouponsListAdapter;
import fakeData.FalseCoupons;

public class Fragment_coupons extends Fragment{

	View v;
    private ArrayList<Coupon> dataCoupons = new ArrayList<Coupon>();
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

		return v;
    }
    public void setCouponList(int type)
    {
		//data ktore vstupuju do adaptera
    	if(dataCoupons.size()>0)dataCoupons.clear();

		if(type == 1) {
			this.getActivity().setTitle("Hot Coupons");
			for (Coupon coupon : CouponSet.getCoupons()) {
				if (coupon.isUsed() == 0) {
					Activity activity = getActivity();
					dataCoupons.add(coupon);
					CouponsListAdapter couponsList = new CouponsListAdapter(activity, dataCoupons,getActivity());
					couponPage.setAdapter(couponsList);
				}
			}
		}
		else {
			this.getActivity().setTitle("Golden Coupon");
			for (Coupon coupon : FalseCoupons.getGolden()) {
				if (coupon.isUsed() == 0) {
					Activity activity = getActivity();
					dataCoupons.add(coupon);
					CouponsListAdapter couponsList = new CouponsListAdapter(activity, dataCoupons,getActivity());
					couponPage.setAdapter(couponsList);
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