package com.habbat.bookable.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by HT-Moh on 18.12.17.
 * Module for DI
 */

@Module
public class RetrofitNetworkServiceApiModule {
    @Provides
    @Singleton
    RetrofitNetworkServiceApi provideRetrofitNetworkServiceApi(){
        return new RetrofitNetworkServiceApi();
    }
}
