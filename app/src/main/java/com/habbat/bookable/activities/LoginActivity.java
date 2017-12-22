package com.habbat.bookable.activities;

/**
 * Created by HT-Moh on 15.12.17.
 * Login activity that manages google OAuth2
 *
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.habbat.bookable.Constants;
import com.habbat.bookable.R;
import com.habbat.bookable.retrofit.OAuthServer;
import com.habbat.bookable.retrofit.OAuthToken;
import com.habbat.bookable.retrofit.RetrofitBuilder;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    /***********************************************************
     * Managing Life Cycle
     **********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Implement efresh token on background before it expires to prevent asking the user for autorization each time
        //Manage the callback case:

    }

    @Override protected void onResume(){
        super.onResume();
        Uri data = getIntent().getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (Constants.REDIRECT_URI_ROOT.equals(data.getScheme())) {
                String ptext = data.toString();
                // We can not query the data directly for custom uri (Error -> This isn't a hierarchical URI.)
                //code = data.getQueryParameter(CODE);
                //error=data.getQueryParameter(ERROR_CODE);

                Constants.code = ptext.substring( ptext.lastIndexOf("code=") + 5);
                if(Constants.code == null || (Constants.code !=null && Constants.code.length()==0)){
                    Constants.error = ptext.substring( ptext.lastIndexOf("error=") + 5);
                }
                Log.e(TAG, "onCreate: handle result of authorization with code :" + Constants.code);
                if (!TextUtils.isEmpty(Constants.code)) {
                    getTokenFormUrl();
                }
                if(!TextUtils.isEmpty(Constants.error)) {
                    //Problem, the user reject our granting request or something like that
                    Toast.makeText(this, R.string.loginactivity_grantsfails_quit,Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onCreate: handle result of authorization with error :" + Constants.error);
                    //then die
                    finish();
                }
            }
        } else {
            // Manage the start application case:
            // If you don't have a token yet or if your token has expired , ask for it
            OAuthToken oauthToken=OAuthToken.Factory.create();
            if (oauthToken==null
                    ||oauthToken.getAccessToken()==null) {
                //first case==first token request
                if(oauthToken==null||oauthToken.getRefreshToken()==null || oauthToken.getAccessToken()==null){
                    Log.e(TAG, "onCreate: Launching authorization (first step)");
                    //first step of OAUth: the authorization step
                    makeAuthorizationRequest();
                }else{
                    Log.e(TAG, "onCreate: refreshing the token :" + oauthToken);
                    //refresh token case
                    refreshTokenFormUrl(oauthToken);
                }
            }
            //Launch Book activity
            else {
                Log.e(TAG, "onCreate: Token available, just launch Book Activity");
                startMainActivity(true,true);
            }
        }
    }
    /***********************************************************
     *  Managing Authotization and Token process
     **********************************************************/

    /**
     * Make the Authorization request
     */

    private void makeAuthorizationRequest() {
        HttpUrl authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
                .newBuilder() //
                .addQueryParameter("client_id", Constants.CLIENT_ID)
                .addQueryParameter("scope", Constants.API_SCOPE)
                .addQueryParameter("redirect_uri", Constants.REDIRECT_URI)
                .addQueryParameter("response_type", Constants.CODE)
                .build();
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.e(TAG, "the url is : " + String.valueOf(authorizeUrl.url()));
        i.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    /**
     * Refresh the OAuth token
     */

    private void refreshTokenFormUrl(OAuthToken oauthToken) {
        OAuthServer oAuthServer = RetrofitBuilder.getSimpleClient(this,false);
        Call<OAuthToken> refreshTokenFormCall = oAuthServer.refreshTokenForm(
                oauthToken.getRefreshToken(),
                Constants.CLIENT_ID,
                Constants.GRANT_TYPE_REFRESH_TOKEN
        );
        refreshTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormUrl succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true,true);
            }

            @Override
            public void onFailure(Call<OAuthToken> call, Throwable t) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call refreshTokenFormCall failed", t);

            }
        });
    }

    /**
     * Retrieve the OAuth token
     */

    private void getTokenFormUrl() {
        OAuthServer oAuthServer = RetrofitBuilder.getSimpleClient(this,false);
        Call<OAuthToken> getRequestTokenFormCall = oAuthServer.requestTokenForm(
                Constants.code,
                Constants.CLIENT_ID,
                Constants.REDIRECT_URI,
                Constants.GRANT_TYPE_AUTHORIZATION_CODE
        );
        getRequestTokenFormCall.enqueue(new Callback<OAuthToken>() {
            @Override
            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {

                Log.e(TAG, "===============New Call==========================");

                Log.e(TAG, "The call getRequestTokenFormCall succeed with code=" + response.code() + " and has body = " + response.body());
                //ok we have the token
                response.body().save();
                startMainActivity(true,true);
            }

            @Override
            public void onFailure(Call<OAuthToken> call, Throwable t) {
                Log.e(TAG, "===============New Call==========================");
                Log.e(TAG, "The call getRequestTokenFormCall failed", t);

            }
        });
    }

    /**
     * Start the next activity
     */
    private void startMainActivity(boolean newtask,boolean goToBooks) {
        Intent i = new Intent(this, MainActivity.class);
        if(newtask){
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        i.putExtra("GETVOLUMESWITHOAUTH",true);
        if (goToBooks){
            i.putExtra("NAVIGATE_TO_BOOKS",true);
        }
        startActivity(i);
        finish();
    }
    void startBookActivity(Boolean isOath){
        Intent booksActivity = new Intent(this,BooksRecycledListView.class);
        booksActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        booksActivity.putExtra("OAUTH",isOath);
        startActivity(booksActivity);
        finish();
    }

}