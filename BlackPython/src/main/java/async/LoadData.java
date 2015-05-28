package async;

import data.Const;
import data.CouponSet;
import data.MemoryStorage;
import data.UserInformation;

import fakeData.FalseCoupons;
import activity.Index;
import manager.SharedPreferencesManager;
import utils.JSONparser;
import utils.JsonCreator;
import utils.LoggingTypes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Random;

public class LoadData {

	private ProgressDialog progressDialog;
	private Context targetCtx;
	public boolean finish = false;
	public LoadData(Context context) {
		this.targetCtx = context;
	}

	public boolean getData(final Context _context, final Index _activity) {

		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

			String result;
			final Context context = _context;
			Index activity = _activity;
			@Override
			protected void onPreExecute() {
				// progressDialog.show ( ) ;
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
					Log.d("Download","problem downloading the data: " + e);
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

			@Override
			public void onPostExecute(Void ret) {
				JSONparser p = new JSONparser();
				p.parse(result,context,activity);
				Log.d("Coupons","finished");
				finish = true;
				//}
			}
		}.execute();
		return true;
	}
}
