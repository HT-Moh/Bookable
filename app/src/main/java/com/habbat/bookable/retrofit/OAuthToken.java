package com.habbat.bookable.retrofit;

/**
 * Created by hackolos on 15.12.17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.habbat.bookable.BookableApplication;


public class OAuthToken {
    private static final String TAG = "OAuthToken";
    /***********************************************************
     * Constants
     **********************************************************/
    private static final String OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs";

    private static final String SP_TOKEN_KEY = "token";
    private static final String SP_TOKEN_TYPE_KEY = "token_type";
    private static final String SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after";
    private static final String SP_REFRESH_TOKEN_KEY = "refresh_token";

    /***********************************************************
     * Attributes
     **********************************************************/
    @SerializedName("access_token")
    @Expose
    private String accessToken;


    @SerializedName("token_type")
    @Expose
    private String tokenType;


    @SerializedName("expires_in")
    @Expose
    private long expiresIn;


    private long expiredAfterMilli = 0;


    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    /***********************************************************
     * Managing Persistence
     **********************************************************/
    public void save() {
        Log.e(TAG, "Savng the following element " + this);
        //update expired_after
        expiredAfterMilli = System.currentTimeMillis() + expiresIn * 1000;
        Log.e(TAG, "Savng the following element and expiredAfterMilli =" + expiredAfterMilli+" where now="+System.currentTimeMillis()+" and expired in ="+ expiresIn);
        SharedPreferences sp = BookableApplication.instance.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(SP_TOKEN_KEY, accessToken);
        ed.putString(SP_TOKEN_TYPE_KEY, tokenType);
        ed.putLong(SP_TOKEN_EXPIRED_AFTER_KEY, expiredAfterMilli);
        ed.putString(SP_REFRESH_TOKEN_KEY, refreshToken);
        ed.commit();
    }



    /***********************************************************
     * Getters and Setters
     **********************************************************/
    public String getAccessToken() {
        return accessToken;
    }


    //TODO use expires to refresh the tocken on background
    public long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    void setExpiredAfterMilli(long expiredAfterMilli) {
        this.expiredAfterMilli = expiredAfterMilli;
    }

    void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OAuthToken{");
        sb.append("accessToken='").append(accessToken).append('\'');
        sb.append(", tokenType='").append(tokenType).append('\'');
        sb.append(", expires_in=").append(expiresIn);
        sb.append(", expiredAfterMilli=").append(expiredAfterMilli);
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /***********************************************************
     * Factory Pattern
     **********************************************************/

    public static class Factory {
        private static final String TAG = "OAuthToken.Factory";

        public static OAuthToken create() {
            long expiredAfter = 0;
            SharedPreferences sp = BookableApplication.instance.getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
            if (sp.contains(SP_TOKEN_EXPIRED_AFTER_KEY)) {
                Log.e(TAG, "sp.contains(SP_TOKEN_EXPIRED_AFTER)");
                expiredAfter = sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0);
                long now = System.currentTimeMillis();

                Log.e(TAG, "Delta : " + (now - expiredAfter));
                if (expiredAfter == 0 || now > expiredAfter) {
                    Log.e(TAG, "expiredAfter==0||now>expiredAfter, token has expired");
                    //flush token in the SP
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString(SP_TOKEN_KEY, null);
                    ed.commit();
                    //rebuild the object according to the SP
                    OAuthToken oauthToken = new OAuthToken();
                    oauthToken.setAccessToken(null);
                    oauthToken.setTokenType(sp.getString(SP_TOKEN_TYPE_KEY, null));
                    oauthToken.setRefreshToken(sp.getString(SP_REFRESH_TOKEN_KEY, null));
                    oauthToken.setExpiredAfterMilli(sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0));
                    return oauthToken;
                } else {

                    Log.e(TAG, "NOT (expiredAfter==0||now<expiredAfter) current case, token is valid");
                    //rebuild the object according to the SP
                    OAuthToken oauthToken = new OAuthToken();
                    oauthToken.setAccessToken(sp.getString(SP_TOKEN_KEY, null));
                    oauthToken.setTokenType(sp.getString(SP_TOKEN_TYPE_KEY, null));
                    oauthToken.setRefreshToken(sp.getString(SP_REFRESH_TOKEN_KEY, null));
                    oauthToken.setExpiredAfterMilli(sp.getLong(SP_TOKEN_EXPIRED_AFTER_KEY, 0));
                    return oauthToken;
                }
            } else {
                return null;
            }
        }
    }

}