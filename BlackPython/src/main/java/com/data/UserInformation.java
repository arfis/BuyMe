package com.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.blackpython.R;
import com.facebook.widget.LoginButton;
import com.utils.HotCoupons;

public class UserInformation {
	public static final String PREFS_NAME = "HCPreferences";
	
	static String name;
	static Object email;
	static String id;
	static int used = 0;
	static int usedCoupons;
	static boolean loggedInFb;
	static LoginButton loggingButton;
	static MemoryStorage mMemoryStorage;
	static SharedPreferences mSharedPreferences;
	
	public static String userIdKey;
	public static String loginTokenKey;
	public static String alreadyLoggedKey;
	
	public static Context context;
	public UserInformation()
	{
		this.context = HotCoupons.getInstance();
	}
	
	public static void LoadShaderReferences()
	{
		SharedPreferences prefs = ((HotCoupons) context).getInstance().getSharedPreferences(
			      PREFS_NAME, Context.MODE_PRIVATE);
		//usedCoupons = prefs.getInt("usedCoupons");
		
	}
	
	@SuppressLint("NewApi") public static void setSharedPreferences()
	{
		SharedPreferences prefs = ((HotCoupons) context).getInstance().getSharedPreferences(
			      PREFS_NAME, Context.MODE_PRIVATE);
		prefs.edit()
		.putBoolean("loggedIn", loggedInFb)
		.putInt("usedCoupons", usedCoupons)
		.commit();
	}
	public static SharedPreferences getLocal() {
		return mSharedPreferences;
	}

	public static void setLocal(SharedPreferences local) {
		mSharedPreferences = local;
	}

	public static MemoryStorage getMemory() {
		return mMemoryStorage;
	}

	public static void setMemory(MemoryStorage memory) {
		mMemoryStorage = memory;
	}

	public static void CouponIncrement()
	{
		used++;
	}
	
	public static LoginButton getLoggingButton() {
		return loggingButton;
	}
	public static void setLoggingButton(LoginButton loggingButton) {
		UserInformation.loggingButton = loggingButton;
	}
	public static boolean isLoggedIn() {
		return loggedInFb;
	}
	public static void setLoggedIn(boolean loggedIn) {
		UserInformation.loggedInFb = loggedIn;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String namee) {
		name = namee;
	}
	public static Object getEmail() {
		return email;
	}
	public static void setEmail(Object object) {
		email = object;
	}
	public static String getId() {
		return id;
	}
	public static void setId(String idd) {
		id = idd;
	}
	public static int getUsedCoupons() {
		return used;
	}
	
}
