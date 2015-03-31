package manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by Snow on 3/21/2015.
 */
public class GPSManager {
    public static void turnGPSOn(Context ctx){
        Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        ctx.sendBroadcast(intent);
    }

    public static void turnGPSOff(Context ctx){
        String provider = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            ctx.sendBroadcast(poke);
        }
    }
}
