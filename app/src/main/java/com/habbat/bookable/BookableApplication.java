package com.habbat.bookable;

/**
 * Created by HT-Moh on 15.12.17.
 */
import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class BookableApplication extends Application implements HasActivityInjector  {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    BookableApplicationComponent BookableApplicationComponent = null;
    public static BookableApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getComponent().inject(this);
    }
    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
    public BookableApplicationComponent getComponent() {
        if (BookableApplicationComponent == null) {
            BookableApplicationComponent = DaggerBookableApplicationComponent.builder().build();
        }
        return BookableApplicationComponent;
    }
}