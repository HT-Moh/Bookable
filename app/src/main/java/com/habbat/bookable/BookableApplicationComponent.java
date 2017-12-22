package com.habbat.bookable;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by HT-Moh on 18.12.17.
 */

@Component(modules = {AndroidInjectionModule.class, BookableApplicationModule.class})
public interface BookableApplicationComponent  extends AndroidInjector<BookableApplication> {
}
