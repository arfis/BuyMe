package activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import async.LoadData;
import manager.GPSManager;
import manager.SharedPreferencesManager;

import com.blackpython.R;
import com.data.UserInformation;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.utils.HotCoupons;
import com.utils.LoggingTypes;

public class LoginActivityFragment extends Fragment {
	ImageButton buttonfreelogin;
	LoginButton authButton;
	ProfilePictureView pf;
	Context context;
	Animation fadeIn;

	private static final String TAG = "LoginActivityFragment";
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	/*
	public static void printHashKey(Context pContext) {
		try {
			PackageInfo info =  getPackageInfo(pContext, PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String hashKey = new String(Base64.encode(md.digest(), 0));
				Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
			}
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "printHashKey()", e);
		} catch (Exception e) {
			Log.e(TAG, "printHashKey()", e);
		}
	}
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		this.context = getActivity().getApplicationContext();
		GPSManager.turnGPSOn(context);
		SharedPreferencesManager.init(context);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activitylogin, container, false);
		ImageView logo
				= (ImageView) view.findViewById(R.id.couponView);

		fadeIn = AnimationUtils.loadAnimation(inflater.getContext(),
				R.anim.fade_in);

		logo.setAnimation(fadeIn);

		authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setAnimation(fadeIn);


		authButton.setReadPermissions(Arrays.asList("email"));
		buttonfreelogin = (ImageButton) view.findViewById(R.id.buttonfreelogin);
		buttonfreelogin.setAnimation(fadeIn);

		addListenerOnButton(view);
		return view;
	}

	public void addListenerOnButton(View v) {
		buttonfreelogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferencesManager.setLoggedMethod(LoggingTypes.FREE.getIntValue());
				  startFirstActivity();
				//new LoadData(context).execute();
			}
		});
	}

	// zmena stavu prihlasenia
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			// make request to the /me API
			Request request = Request.newMeRequest(session,
					new Request.GraphUserCallback() {
						// callback after Graph API response with user object
						// uspesne prihlasenie cez FB
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							// TODO Auto-generated method stub
							if (user != null) {
								// ziskanie informacii do statickej triedy
								// UserInformation
								UserInformation.setLoggedIn(true);
								UserInformation.setLoggingButton(authButton);
								UserInformation.setName(user.getName());
								UserInformation.setId(user.getId());
								UserInformation.setEmail(user.asMap().get(
										"email"));

								SharedPreferencesManager.setLoggedMethod(LoggingTypes.FACEBOOK.getIntValue());
								startFirstActivity();
							}
						}

					});
			
			Request.executeBatchAsync(request);
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}
	
	private void startFirstActivity() {
	 	Intent intent = new Intent(context,Index.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		startActivity(intent);
		this.getActivity().finish();
		this.getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}