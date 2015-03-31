package com.utils;

import android.content.Context;

public class HotCoupons extends android.app.Application{

	  private static Context context;
	  private static HotCoupons singleton;
	  //private static final String PROPERTY_ID;
	  private String DOUBLE_LINE_SEP = "\n\n";
	  private String SINGLE_LINE_SEP = "\n";


	    // The following line should be changed to include the correct property id.

	    //Logging TAG
	  private static final String TAG = "Hot Coupons";


	  //toto je trieda pre tracker - ak nejaky vobec bude
	  public HotCoupons() {
		  super();
	    }

	    public static HotCoupons getInstance() {
	        return singleton;
	    }
	    
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        singleton = this;
	        HotCoupons.context = getApplicationContext();
	    }
	    
	    public Context getAppContext()
	    {
	    	return HotCoupons.context;
	    }

}
