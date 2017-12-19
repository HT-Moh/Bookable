package com.habbat.bookable.retrofit;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hackolos on 18.12.17.
 */

@Singleton
@Component(modules = RetrofitNetworkServiceApiModule.class)
public interface RetrofitNetworkServiceApiComponent {
    RetrofitNetworkServiceApi provideRetrofitNetworkServiceApi();
}
