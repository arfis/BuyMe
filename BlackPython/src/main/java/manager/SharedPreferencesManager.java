package manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {
    static String TAG_LOG  = "LoggedFB";
    static String TAG_COUPONS_USED = "UsedCoupons";
    static String TAG_COUPONS_USED_ID = "UsedCouponsString";
    static String TAG_LOCATION = "location";
    static String TAG_EMAIL = "email";
    static String TAG_NEW = "new";
    static String TAG_NAME = "name";
    static String TAG_FB_ID = "fbID";

    static SharedPreferences sharedPrefs;
    static Context context;

    public static void init(Context cx){
        context = cx;
        if(sharedPrefs == null)
            sharedPrefs = context.getSharedPreferences("wifi", context.MODE_PRIVATE);
    }
    public static void setNew(int newCoup){
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt(TAG_NEW, newCoup).commit();
    }

    public static int getNew(){
        return sharedPrefs.getInt(TAG_NEW, 0);

    }

    public static void decreaseNew(){
        int count = sharedPrefs.getInt(TAG_NEW, 0);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt(TAG_NEW, count--).commit();
    }


    public static void setFbID(String id)
    {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(TAG_FB_ID, id).commit();
    }

    public static String getFbID()
    {
        return sharedPrefs.getString(TAG_FB_ID, "");
    }

    public static void setName(String name)
    {
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(TAG_NAME, name).commit();
    }

    public static String getName()
    {
        return sharedPrefs.getString(TAG_NAME, "");
    }

    public static void setEmail(String Email){
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(TAG_EMAIL, Email).commit();
    }

    public static String getEmail(){
        return sharedPrefs.getString(TAG_EMAIL, "");

    }

    public static void setLocationSetting(boolean location){
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putBoolean(TAG_LOCATION,location).commit();
    }

    public static boolean getLocationSetting(){
        return sharedPrefs.getBoolean(TAG_LOCATION,true);
    }

    public static void setLoggedMethod(int facebook){
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt(TAG_LOG,facebook).commit();
    }

    public static int getLoggedMethod(){
        return sharedPrefs.getInt(TAG_LOG,-1);
    }

    public static void setUsedCoupon(int used){
        int count;
        String couponID = "";

        SharedPreferences.Editor edit = sharedPrefs.edit();
        count = sharedPrefs.getInt(TAG_COUPONS_USED,0);
        couponID = sharedPrefs.getString(TAG_COUPONS_USED_ID, couponID);
        couponID += used+";";
        count++;
        Log.i("Shared", "Number of coupons used is: " + count);
        edit.putInt(TAG_COUPONS_USED,count).commit();
        edit.putString(TAG_COUPONS_USED_ID, couponID).commit();
    }

    public static String getUsedCouponsString(){
        return sharedPrefs.getString(TAG_COUPONS_USED_ID,"");
    }

    public static void removeUsedCouponsString(){
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString(TAG_COUPONS_USED_ID, "").commit();
    }

    public static int getUsedCoupons(){
        return sharedPrefs.getInt(TAG_COUPONS_USED,0);
    }
}
