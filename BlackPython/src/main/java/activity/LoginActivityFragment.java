package activity;

import java.util.Arrays;
import java.util.Set;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.app.ToolbarActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import manager.SharedPreferencesManager;
import com.blackpython.R;
import data.UserInformation;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import utils.LoggingTypes;

public class LoginActivityFragment extends Fragment
{

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

	GoogleConnection googleConnection;

	ImageButton buttonfreelogin;
	LoginButton authButton;
	SignInButton googleLogin;
	Context context;
	LoginActivity activity;
	Animation fadeIn;

	/* Request code used to invoke sign in user interactions. */
	private static final int GOOGLE_SIGN_IN = 0;
    private static final int FACEBOOK_SIGN_IN = 64206;

	/* Client used to interact with Google APIs. */
	private static final String TAG = "LoginActivityFragment";
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		this.context = getActivity().getApplicationContext();
		activity = (LoginActivity) getActivity();
		//GPSManager.turnGPSOn(context);
		//SharedPreferencesManager.init(context);
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

		googleLogin = (SignInButton) view.findViewById(R.id.google);
		buttonfreelogin.setAnimation(fadeIn);

		addListenerOnButton(view);
		return view;
	}

	public void addListenerOnButton(View v) {

		authButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!InternetValidation.haveNetworkConnection(activity)) {
					Toast.makeText(getActivity(), "Internet Connection Required.", Toast.LENGTH_LONG);
				}
				else
				{
					//onSessionStateChange(Session.getActiveSession(), SessionState.OPENING, new Exception());
				}
			}
		});

		buttonfreelogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferencesManager.setLoggedMethod(LoggingTypes.FREE.getIntValue());
				startFirstActivity();

				//new LoadData(context).execute();
			}
		});
		googleLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(view.getId() == R.id.google) {
					googleConnection = new GoogleConnection();
					Bundle bundle = new Bundle();
					bundle.putString("action", GoogleConnection.ACTION_CONNECT);
					googleConnection.setArguments(bundle);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.add(getId(), googleConnection)
							.commit();
				}
			}
		});
	}

	// zmena stavu prihlasenia
	private void onSessionStateChange(Session session, SessionState state, Exception exception)
	{
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
								UserInformation.setName(user.getName());
								UserInformation.setFacebookID(user.getId());
								UserInformation.setEmail((String) user.getProperty("email"));

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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == GOOGLE_SIGN_IN)
		{
			if (googleConnection != null)
			{
				googleConnection.onActivityResult(requestCode,resultCode,data);
			}
		}
		else
		{
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
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