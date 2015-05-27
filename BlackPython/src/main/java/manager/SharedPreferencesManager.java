package manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {
    static String TAG_LOG  = "LoggedFB";
    static String TAG_COUPONS_USED = "UsedCoupons";
    static String TAG_LOCATION = "location";

    static SharedPreferences sharedPrefs;
    static Context context;

    public static void init(Context cx){
        context = cx;
        if(sharedPrefs == null)
            sharedPrefs = context.getSharedPreferences("wifi", context.MODE_PRIVATE);
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

    public static void setUsedCoupon(){
        int count;

        SharedPreferences.Editor edit = sharedPrefs.edit();
        count = sharedPrefs.getInt(TAG_COUPONS_USED,0);
        count++;
        Log.i("Shared", "Number of coupons used is: " + count);
        edit.putInt(TAG_COUPONS_USED,count).commit();
    }

    public static int getUsedCoupons(){
        return sharedPrefs.getInt(TAG_COUPONS_USED,0);
    }
}
