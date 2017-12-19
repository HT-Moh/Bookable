package com.habbat.bookable.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hackolos on 18.12.17.
 */

@Module
public class RetrofitNetworkServiceApiModule {
    @Provides
    @Singleton
    RetrofitNetworkServiceApi provideRetrofitNetworkServiceApi(){
        return new RetrofitNetworkServiceApi();
    }
}
