package async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import activity.Index;
import data.Const;
import manager.SharedPreferencesManager;
import utils.JSONparser;
import utils.JsonCreator;
import utils.LoggingTypes;

/**
 * Created by HomePC on 31.5.2015.
 */
public class LoadData2 extends AsyncTask<Void, Void, Void> {

    String result;
    Context context;
    Index activity;

    public LoadData2(Context context, Index activity)
    {
        this.context = context;
        this.activity = activity;
    }


    @Override
    public void onPostExecute(Void ret) {
        JSONparser p = new JSONparser();
        p.parse(result, context, activity);
        Log.d("Coupons","finished");
    }

    @Override
    protected Void doInBackground(Void... contexts) {
        InputStream inputStream = null;
        Random rand = new Random();

        int  n = rand.nextInt(10000) + 1;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpPost;

        httpPost = new HttpGet(Const.LINK + Const.IMEI + n + "/");
        try {

            if(SharedPreferencesManager.getLoggedMethod() == LoggingTypes.GMAIL.getIntValue() ||
                    SharedPreferencesManager.getLoggedMethod() == LoggingTypes.FACEBOOK.getIntValue()) {

                String url = Const.LINK + Const.IMEI + n + "?email=" + SharedPreferencesManager.getEmail() + n;
                httpPost = new HttpGet(url);
                JsonCreator jcreat = new JsonCreator();
                httpPost .setHeader("DATA", jcreat.createMail(SharedPreferencesManager.getEmail()).toString(2));
            }

            //httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (Exception e) {
            Log.d("Download", "problem downloading the data: " + e);
        }
        ;

        // precitanie inputStreamu z requestu
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();

            String line = null;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            result = sBuilder.toString();

        } catch (Exception e) {
            Log.e("StringBuilding", "Error converting result " + e.toString());
        }
        return null;
    }

}
