package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.blackpython.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import data.UserInformation;
import manager.SharedPreferencesManager;
import utils.LoggingTypes;

/**
 * Created by Snow on 5/27/2015.
 */
public class GoogleConnection extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
    public static String ACTION_CONNECT = "CONNECT";
    public static String ACTION_DISCONNECT = "DISCONNECT";
    public String action = "";

    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private int mSignInProgress;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);

        mGoogleApiClient = buildGoogleApiClient();
        action = this.getArguments().getString("action");

        mSignInProgress = STATE_SIGN_IN;
        mGoogleApiClient.connect();
    }

    private GoogleApiClient buildGoogleApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity().getApplicationContext(), this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_PROFILE);

        return builder.build();
    }

    @Override
    public void onConnected(Bundle connectionHint)
    {
        if (action == ACTION_CONNECT)
        {
            if (SharedPreferencesManager.getGoogleShadow())
            {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
            }

            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            //if it is a google+ account currentPerson returns a value, else it is null
            if (currentPerson != null) {
                String name = currentPerson.getDisplayName();
                UserInformation.setGoogleUserImage(currentPerson.getImage().getUrl());
                UserInformation.setName(name);
            }

            UserInformation.setEmail(email);
            UserInformation.setLoggedMethod(LoggingTypes.GMAIL.getIntValue());
            UserInformation.setGoogleApiClient(mGoogleApiClient);
            startFirstActivity();
            mSignInProgress = STATE_DEFAULT;
        }
        else if (action == ACTION_DISCONNECT)
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        dialog.dismiss();

    }

    private void startFirstActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(),Index.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.getActivity().finish();
        this.getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected PendingIntent mSignInIntent;
    private int mSignInError;
    private String TAG = "G+";
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
        dialog.dismiss();

        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE ||
            result.getErrorCode() == ConnectionResult.TIMEOUT ||
            result.getErrorCode() == ConnectionResult.INTERRUPTED ||
            result.getErrorCode() == ConnectionResult.NETWORK_ERROR )
        {
            if (action == ACTION_CONNECT)
            {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error Occured")
                        .setMessage("Internet Connection Required")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else if (action == ACTION_DISCONNECT)
            {
                UserInformation.clearUserData();
                SharedPreferencesManager.setGoogleShadow(true);
            }

        } else if (mSignInProgress != STATE_IN_PROGRESS) {

            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN && result.hasResolution()) {

                try {

                    mSignInProgress = STATE_IN_PROGRESS;
                    result.startResolutionForResult(getActivity(), RC_SIGN_IN);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == getActivity().RESULT_OK) {
                    dialog.setMessage("Signing in.");
                    dialog.show();
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
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
}
