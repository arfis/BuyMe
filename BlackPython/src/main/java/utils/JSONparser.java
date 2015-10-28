package utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import data.DBCoupon;
import data.MemoryStorage;
import data.UserInformation;
import manager.SharedPreferencesManager;

public class JSONparser {
    CouponSet coupons;
    ArrayList<DBCoupon> coupList = new ArrayList<DBCoupon>();
    MemoryStorage db;
    DBCoupon dbcoup;
    int couponNumber = 0;

    public CouponSet parse(String results,Context ctx,Index runningActivity){
        coupons = new CouponSet();

        try {

            JSONArray jsonCoupons = new JSONArray(results);
            if(jsonCoupons.length() > 0) {
               if(!db.isDatabaseEmpty() ) {
                   db.deleteAllCoupons();
               }
                //nadstavenie poctu novych kuponov
                SharedPreferencesManager.setNew(jsonCoupons.length());

                for (int i = 0; i < jsonCoupons.length(); i++) {
                    Coupon coup = new Coupon();
                    coup.setAbout(((JSONObject) jsonCoupons.get(i)).get("about").toString());
                    coup.setPermission(getInt(Boolean.valueOf(((JSONObject) jsonCoupons.get(i)).get("mature").toString())));
                    //coup.setId(Integer.valueOf(((JSONObject) jsonCoupons.get(i)).get("id").toString()));
                    coup.setId(i);
                    coup.setUsed(0);
                    coup.setTitle(((JSONObject) jsonCoupons.get(i)).get("name").toString());
                    //coup.setCompany(((JSONObject) jsonCoupons.get(i)).get("company").toString());
                    coup.setPrice(((JSONObject) jsonCoupons.get(i)).get("discount").toString());
                    getPicture(((JSONObject) jsonCoupons.get(i)).get("picture").toString(), coup, 1, runningActivity);
                    getPicture(((JSONObject) jsonCoupons.get(i)).get("code").toString(), coup, 2, runningActivity);

                }

                coupons.setCoupons(coupList);
            }
            //ak nie su kupony stiahnute - ziadne nove - tak sa nacitaju kupony z databazy
            else{
                coupons.setCoupons(DBCoupon.find(DBCoupon.class, null, null));
            }
        }catch(Exception e){
            Log.d("parseError", e.toString());
        };
        return coupons;
    }
    public int getInt(boolean bool){
        if(bool == true) return 0;
        else return 1;
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
                    HttpGet httpPost = new HttpGet(link);
                    //httpPost.setEntity(new UrlEncodedFormEntity(param));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    inputStreamPicture = httpEntity.getContent();

                    //Deafult Coupon Picture
                    if (inputStreamPicture == null) {
                        httpPost = new HttpGet(Const.DEFAULT_PIC);
                        httpResponse = httpClient.execute(httpPost);
                        httpEntity = httpResponse.getEntity();
                        inputStreamPicture = httpEntity.getContent();
                    }
                    
                    blob =  getByteStream(inputStreamPicture);
                    if(type == 1)
                        coup.setPicture(blob);
                    else {
                        coup.setCode(blob);
                        //db.addCoupon(coup); String title, int permission, String price, String about, int used, byte[] picture, String company, byte[] code, int opened
                        dbcoup = new DBCoupon(coup.getTitle(),coup.isPermission(),coup.getPrice(),coup.getAbout(),coup.isUsed(),coup.getPictureByte(),coup.getCompany(),coup.getCodeByte(),0,couponNumber);
                        coupList.add(dbcoup);
                        runningActivity.refresh();
                        dbcoup.setId((long)1);
                        couponNumber++;
                        dbcoup.save();
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
