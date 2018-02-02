package com.habbat.bookable.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Bookable: Google book API
 * Created by Mohamed Habbat on 01.02.18.
 * www.habbat.ch Habbat C&D
 */
@Module
public class RetrofitNeworkServiceModule {
    @Provides
    @Singleton
    RetrofitNetworkService provideRetrofitNetworkServiceApi(){
        return new RetrofitNetworkServiceApi();
    }
}
