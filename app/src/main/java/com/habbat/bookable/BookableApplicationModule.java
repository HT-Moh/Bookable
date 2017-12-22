package com.habbat.bookable;

import com.habbat.bookable.activities.BooksRecycledListView;
import com.habbat.bookable.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by HT-Moh on 18.12.17.
 * DI Component
 */
@Module
public abstract class BookableApplicationModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeActivityInjector();
    @ContributesAndroidInjector
    abstract BooksRecycledListView contributeBooksActivity();
}
