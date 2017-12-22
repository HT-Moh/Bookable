package com.habbat.bookable.helpers;

import android.content.Context;

import com.robertsimoes.shareable.Shareable;

/**
 * Bookable: Google book API
 * Created by HT-Moh on 22.12.17.
 * www.habbat.ch Habbat C&D
 */


public class ShareClass {
    private static ShareClass shareClass = null;
    private ShareClass() {};

    public static synchronized ShareClass getInstance() {
        if (shareClass == null) {
            shareClass = new ShareClass();
        }
        return(shareClass);
    }


    public void twitter(String message, String url, Context context){
        Shareable shareAction = new Shareable.Builder(context)
                .message(message)
                .url(url)
                .socialChannel(Shareable.Builder.TWITTER)
                .build();
        shareAction.share();
    }
    public void facebook(String message, String url, Context context){
        Shareable shareAction = new Shareable.Builder(context)
                .message(message)
                .url(url)
                .socialChannel(Shareable.Builder.FACEBOOK)
                .build();
        shareAction.share();
    }
}
