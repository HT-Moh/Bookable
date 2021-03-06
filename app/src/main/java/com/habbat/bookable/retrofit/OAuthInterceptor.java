package com.habbat.bookable.retrofit;
/**
 * Bookable: Google book API
 * Created by HT-Moh on 15.12.17.
 * www.habbat.ch Habbat C&D
 *
 */


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 *OAuthInterceptor interceptor responsible to add access token to rest calls
 *
 */

public class OAuthInterceptor implements Interceptor {
    private static final String TAG = "OAuthInterceptor";
    private String accessToken, accessTokenType;

    @Override
    public Response intercept(Chain chain) throws IOException {
        //find the token
        OAuthToken oauthToken = OAuthToken.Factory.create();
        accessToken = oauthToken.getAccessToken();
        accessTokenType = oauthToken.getTokenType();
        //add it to the request
        Request.Builder builder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(accessTokenType)) {
            Log.e(TAG, "In the interceptor adding the header authorization with : " + accessTokenType + " " + accessToken);
            builder.header("Authorization", accessTokenType + " " + accessToken);
        } else {
           throw new IOException("the access token seems to not exist");
        }
        //proceed to the call
        return chain.proceed(builder.build());
    }
}