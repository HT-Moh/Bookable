package com.habbat.bookable.retrofit; /**
 * Bookable: Google book API
 * Created by HT-Moh on 15.12.17.
 * www.habbat.ch Habbat C&D
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    /**
     * Root URL
     */
    public static final String BASE_URL = "https://www.googleapis.com/";

    /**
     * Getting RestCalls instance using Retrofit creation
     * A basic client to make unauthenticated calls
     * @param ctx
     * @return OAuthServerIntf instance
     */
    public static RestCalls getSimpleClient(Context ctx, Boolean RxJavaAdapter) {

        //Using Default HttpClient
        Retrofit retrofit;
        if (RxJavaAdapter){
            retrofit= new Retrofit.Builder()
                    .client(getSimpleOkHttpClient(ctx))
                    .addConverterFactory(new StringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        else {
            retrofit= new Retrofit.Builder()
                    .client(getSimpleOkHttpClient(ctx))
                    .addConverterFactory(new StringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit.create(RestCalls.class);
    }

    /**
     * An autenticated client to make authenticated calls
     * The token is automaticly added in the Header of the request
     * @param ctx
     * @return RestCalls instance
     */
    protected static RestCalls getOAuthClient(Context ctx) {
        Retrofit raCustom = new Retrofit.Builder()
                .client(getOAuthOkHttpClient(ctx))
                .baseUrl(BASE_URL)
                .addConverterFactory(new StringConverterFactory())
               // .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return raCustom.create(RestCalls.class);
    }


    /**
     * Return a simple OkHttpClient v:
     * have a cache
     * have a HttpLogger
     * @param ctx
     */
    @NonNull
    private static OkHttpClient getSimpleOkHttpClient(Context ctx) {
        // Define the OkHttp Client with its cache!
        // Assigning a CacheDirectory
        File myCacheDir=new File(ctx.getCacheDir(),"OkHttpCache");
        // Let's use the cache.
        int cacheSize=1024*1024;
        Cache cacheDir=new Cache(myCacheDir,cacheSize);
        //HttpLoggingInterceptor httpLogInterceptor=new HttpLoggingInterceptor();
        //httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                //add a cache
                  .cache(cacheDir)
                //.addInterceptor(httpLogInterceptor)
                .build();
    }

    /**
     * Return a OAuth OkHttpClient v:
     * have a cache
     * have a HttpLogger
     * add automaticly the token in the header of each request because of the oAuthInterceptor
     * @param ctx
     * @return
     */
    @NonNull
    private static OkHttpClient getOAuthOkHttpClient(Context ctx) {
        // Define the OkHttp Client with its cache!
        // Assigning a CacheDirectory
        File myCacheDir=new File(ctx.getCacheDir(),"OkHttpCache");
        // You should create it...
        int cacheSize=1024*1024;
        Cache cacheDir=new Cache(myCacheDir,cacheSize);
        //TODO Implement DI for the intrceptor
        Interceptor oAuthInterceptor=new OAuthInterceptor();
        //HttpLoggingInterceptor httpLogInterceptor=new HttpLoggingInterceptor();
        //httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(cacheDir)
                .addInterceptor(oAuthInterceptor)
               // .addInterceptor(httpLogInterceptor)
                .build();
    }
}