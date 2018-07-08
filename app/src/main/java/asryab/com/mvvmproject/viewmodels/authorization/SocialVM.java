package asryab.com.mvvmproject.viewmodels.authorization;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.screens.authorization.login.LoginActivity;

public class SocialVM implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 10;
    private static final String LOG_TAG = SocialVM.class.getSimpleName();
    private  GoogleApiClient mGoogleApiClient;
//    private Callback<TwitterSession> twitterCallback;
//    private TwitterCore core;
    private CallbackManager callbackManager;
    private LoginActivity mActivity;
    private SocialListener mSocialListener;

    public SocialVM(LoginActivity activity, SocialListener socialListener) {
        mSocialListener = socialListener;
        mActivity = activity;
        FacebookSdk.sdkInitialize(activity);
        AppEventsLogger.activateApp(activity);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(LOG_TAG,"facebook.onSuccess");
                User user = new User();
                setResult(user);
            }

            @Override
            public void onCancel() {
                Log.d(LOG_TAG,"facebook.onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(LOG_TAG,"facebook.onError");
            }
        });
//

//        core = Twitter.getInstance().core;
//        twitterCallback = new Callback<TwitterSession>() {
//            @Override
//            public void success(Result<TwitterSession> result) {
//                Log.d(LOG_TAG,"twitter.success");
//                User user = new User();
//                setResult(user);
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d(LOG_TAG,"twitter.failure.exception="+exception.getMessage());
//            }
//        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setResult(User user) {
        mSocialListener.socialUser(user);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(LOG_TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User();
            setResult(user);
        } else {
        }
    }

    public void clickFaceBook() {
        Log.d(LOG_TAG,"clickFaceBook");
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("public_profile"));
    }

    public void clickTwitter() {

        Log.d(LOG_TAG,"clickTwitter");
//        core.logIn(mActivity, twitterCallback);
    }

    public void clickGooglePlus() {
        Log.d(LOG_TAG,"clickGooglePlus");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
