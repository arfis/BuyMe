package data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person;

import manager.SharedPreferencesManager;
import utils.HotCoupons;

public class UserInformation {
	public static final String PREFS_NAME = "HCPreferences";

    //GOOGLE
    static String googleUserImage;
    static GoogleApiClient googleApiClient;

    //COUPONS
    static int used = 0;
	static boolean loggedInFb;
    static MemoryStorage mMemoryStorage;

	public static String userIdKey;
	public static String loginTokenKey;
	public static String alreadyLoggedKey;

	public static void init(Activity activity)
	{
        SharedPreferencesManager.init(activity);
	}

    public static MemoryStorage getMemory() {
        return mMemoryStorage;
    }

    public static void setMemory(MemoryStorage memory) {
        mMemoryStorage = memory;
    }

	public static boolean isLoggedIn() {
		return loggedInFb;
	}
	public static void setLoggedIn(boolean loggedIn) {
		UserInformation.loggedInFb = loggedIn;
	}

    public static void setGoogleUserImage(String img)
    {
        googleUserImage = img;
    }
    public static String getGoogleUserImage()
    {
        return googleUserImage;
    }

    public static int getLoggedMethod()
    {
        return SharedPreferencesManager.getLoggedMethod();
    }
    public static void setLoggedMethod(int method)
    {
        SharedPreferencesManager.setLoggedMethod(method);
    }
    private static void clearLoggedMethod()
    {
        SharedPreferencesManager.setLoggedMethod(-1);
    }


    public static String getName() {
        return SharedPreferencesManager.getName();
    }
    public static void setName(String name) {
        SharedPreferencesManager.setName(name);
    }
    private static void clearName()
    {
        SharedPreferencesManager.setName("");
    }

	public static void setEmail(String mail){
		SharedPreferencesManager.setEmail(mail);
	}
	public static String getEmail(){
		return SharedPreferencesManager.getEmail();
	}
    private static void clearEmail()
    {
        SharedPreferencesManager.setEmail("");
    }

    public static void clearUserData()
    {
        clearEmail();
        clearName();
        clearFacebookID();
        clearLoggedMethod();
    }

	public static String getFacebookID() {
		return SharedPreferencesManager.getFbID();
	}
	public static void setFacebookID(String id) {
		SharedPreferencesManager.setFbID(id);
	}
    public static void clearFacebookID()
    {
        SharedPreferencesManager.setFbID("");
    }

	public static int getUsedCoupons() {
		return used;
	}

    public static GoogleApiClient getGoogleApiClient()
    {
        return googleApiClient;
    }

    public static void setGoogleApiClient(GoogleApiClient client)
    {
        googleApiClient = client;
    }

}
