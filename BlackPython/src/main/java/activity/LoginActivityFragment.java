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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class LoginActivityFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

	ImageButton buttonfreelogin;
	LoginButton authButton;
	SignInButton googleLogin;
	Context context;
	Activity activity;
	Animation fadeIn;

	/* Request code used to invoke sign in user interactions. */
	private static final int GOOGLE_SIGN_IN = 0;
    private static final int FACEBOOK_SIGN_IN = 64206;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;
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
		activity = getActivity();
		//GPSManager.turnGPSOn(context);
		//SharedPreferencesManager.init(context);
        mGoogleApiClient = buildGoogleApiClient();
	}

    private GoogleApiClient buildGoogleApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity()
                .getApplicationContext(), this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_PROFILE);

        return builder.build();
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
				if(view.getId() == R.id.google && !mGoogleApiClient.isConnecting()) {
					mSignInProgress = STATE_SIGN_IN;
					mGoogleApiClient.connect();
				}
			}
		});
	}

    private int mSignInProgress;
    @Override
    public void onConnected(Bundle connectionHint) {

        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

		//if it is a google+ account currentPerson returns a value, else it is null
		if (currentPerson != null) {
			String name = currentPerson.getDisplayName();
            UserInformation.setGoogleUserImage(currentPerson.getImage().getUrl());
			UserInformation.setName(name);
			Log.d("MY NAME: ", name);
		}

        Log.d("MY EMAIL: ",email);
		UserInformation.setEmail(email);
        UserInformation.setLoggedMethod(LoggingTypes.GMAIL.getIntValue());
        UserInformation.setGoogleApiClient(mGoogleApiClient);
        startFirstActivity();
        //Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
        //       .setResultCallback(this);

        // Indicate that the sign in process is complete.
        mSignInProgress = STATE_DEFAULT;
    }

    @Override
    public void onStart() {
        super.onStart();
        //mGoogleApiClient.connect();
    }

    protected PendingIntent mSignInIntent;
    private int mSignInError;
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            Log.w(TAG, "API Unavailable.");
        } else if (mSignInProgress != STATE_IN_PROGRESS) {

            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN && result.hasResolution()) {

                try {

                    mSignInProgress = STATE_IN_PROGRESS;
                    result.startResolutionForResult(activity, GOOGLE_SIGN_IN);

                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "Sign in intent could not be sent: "
                            + e.getLocalizedMessage());
                    mSignInProgress = STATE_SIGN_IN;
                    mGoogleApiClient.connect();
                }
            }
        }

        onSignedOut();
    }

    private void onSignedOut()
    {

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
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        switch (requestCode) {
            case GOOGLE_SIGN_IN:
                if (resultCode == activity.RESULT_OK) {
                    // If the error resolution was successful we should continue
                    // processing errors.
                    mSignInProgress = STATE_SIGN_IN;
                } else {
                    // If the error resolution was not successful or the user canceled,
                    // we should stop processing errors.
                    mSignInProgress = STATE_DEFAULT;
                }
                Log.d("GOOGLE API: ","result code - "+resultCode);
                if (!mGoogleApiClient.isConnecting()) {
                    // If Google Play services resolved the issue with a dialog then
                    // onStart is not called so we need to re-attempt connection here.
                    mGoogleApiClient.connect();
                }
                break;
            case FACEBOOK_SIGN_IN:
                uiHelper.onActivityResult(requestCode, resultCode, data);
                break;
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

	@Override
	public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
	}
}