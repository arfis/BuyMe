package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import activity.Index;
import data.Const;
import data.Coupon;
import data.CouponSet;
import data.MemoryStorage;
import data.UserInformation;

public class JSONparser {
    CouponSet coupons;
    ArrayList<Coupon> coupList = new ArrayList<Coupon>();
    MemoryStorage db;

    public CouponSet parse(String results,Context ctx,Index runningActivity){
        coupons = new CouponSet();
        db = new MemoryStorage(ctx);
        UserInformation.setMemory(db);

        try {
            JSONObject mainObject = new JSONObject(results);

            int objectSize = mainObject.length();

            JSONObject info = mainObject.getJSONObject("information");

            String activated = info.get("activated").toString();
            String city = info.get("city").toString();

            JSONArray jsonCoupons = mainObject.getJSONArray("coupons");
            //JSONObject serviceObject = mainObject.getJSONObject("services");
            int numberCoupons = jsonCoupons.length();

            if(db.isDatabaseEmpty()) {
                for (int i = 0; i < numberCoupons; i++) {
                    Coupon coup = new Coupon();
                    coup.setAbout(((JSONObject) jsonCoupons.get(i)).get("about").toString());
                    coup.setPermission(Integer.valueOf(((JSONObject) jsonCoupons.get(i)).get("mature").toString()));
                    coup.setId(Integer.valueOf(((JSONObject) jsonCoupons.get(i)).get("id").toString()));
                    coup.setUsed(0);
                    coup.setTitle(((JSONObject) jsonCoupons.get(i)).get("name").toString());
                    coup.setCompany(((JSONObject) jsonCoupons.get(i)).get("company").toString());
                    coup.setPrice(((JSONObject) jsonCoupons.get(i)).get("discount").toString());
                    getPicture(((JSONObject) jsonCoupons.get(i)).get("picture").toString(), coup, 1, runningActivity);
                    getPicture(((JSONObject) jsonCoupons.get(i)).get("code").toString(), coup,2, runningActivity);

                }

                coupons.setCoupons(coupList);
            }

            else coupons.setCoupons(db.getAllCoupons());
        }catch(Exception e){
            Log.d("parseError", e.toString());
        };
        return coupons;
    }

    public void getPicture(final String link,final Coupon _coup,final int _type, final Index _runningActivity){

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            InputStream inputStreamPicture = null;
            Coupon coup = _coup;
            Index runningActivity = _runningActivity;
            int type = _type;
            byte[] blob;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(link);
                    //httpPost.setEntity(new UrlEncodedFormEntity(param));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    inputStreamPicture = httpEntity.getContent();
                    blob =  getByteStream(inputStreamPicture);
                    if(type == 1)
                        coup.setPicture(blob);
                    else {
                        coup.setCode(blob);
                        coupList.add(coup);
                        db.addCoupon(coup);
                        runningActivity.refresh();
                        Log.d("Download","coupon added to others");
                    }
                    Log.d("Download","Picture downloaded");
                } catch (Exception e) {
                    Log.d("Picture", "Error: " + e.toString());
                };
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try{
                    Log.d("Download","Closing inputStream");
                    inputStreamPicture.close();
                }catch(Exception e){};
            }
        }.execute();
    }

    private byte[] getByteStream(InputStream is)  {

        try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            // and then we can return your byte array.
            return byteBuffer.toByteArray();
        }catch (Exception e){
            Log.d("ByteConverter","Error: " + e.toString());
        };
        return null;
    }
}