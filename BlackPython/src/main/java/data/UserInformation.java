package data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.widget.LoginButton;

import manager.SharedPreferencesManager;
import utils.HotCoupons;

public class UserInformation {
	public static final String PREFS_NAME = "HCPreferences";
	
	static String name;
	static Object email;
	static String id;
	static int used = 0;
	static boolean loggedInFb;
	static LoginButton loggingButton;
	static MemoryStorage mMemoryStorage;

	public static String userIdKey;
	public static String loginTokenKey;
	public static String alreadyLoggedKey;
	
	public static Context context;
	public UserInformation()
	{
		this.context = HotCoupons.getInstance();
	}

	public static MemoryStorage getMemory() {
		return mMemoryStorage;
	}

	public static void setMemory(MemoryStorage memory) {
		mMemoryStorage = memory;
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
	public static void setEmai(String mail){
		SharedPreferencesManager.setEmail(mail);
	}
	public static String getEmailString(){
		return SharedPreferencesManager.getEmail();
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
