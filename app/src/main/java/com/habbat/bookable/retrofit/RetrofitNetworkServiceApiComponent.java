package com.habbat.bookable.retrofit;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by HT-Moh on 18.12.17.
 * Component for DI
 */

@Singleton
@Component(modules = RetrofitNetworkServiceApiModule.class)
public interface RetrofitNetworkServiceApiComponent {
    RetrofitNetworkServiceApi provideRetrofitNetworkServiceApi();
}
