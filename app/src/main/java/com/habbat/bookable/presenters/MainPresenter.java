package com.habbat.bookable.presenters;

import android.content.Context;

import com.habbat.bookable.Constants;
import com.habbat.bookable.retrofit.RestCalls;
import com.habbat.bookable.retrofit.RetrofitBuilder;
import com.habbat.bookable.retrofit.RetrofitNetworkServiceApi;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Bookable: Google book API
 * Created by Mohamed Habbat on 04.02.18.
 * www.habbat.ch Habbat C&D
 */

public class MainPresenter implements MainContract.Presenter {
    @NonNull
    private Scheduler backgroundScheduler;

    @NonNull
    private Scheduler mainScheduler;

    @NonNull
    private CompositeDisposable subscriptions;

    private MainContract.View view;

    //Inject API Service
    @Inject
    RetrofitNetworkServiceApi retrofitNetworkServiceApi;

    RestCalls restcalls = null;

    public MainPresenter(
            @NonNull Scheduler backgroundScheduler,
            @NonNull Scheduler mainScheduler,
            MainContract.View view) {
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        this.view = view;
        subscriptions = new CompositeDisposable();
        restcalls = RetrofitBuilder.getSimpleClient((Context)view,true);
    }
    @Override
    public void subscribe(String query) {
        loadData(query);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void onDestroy() {
        this.view = null;
        restcalls = null;
    }
    @Override
    public void loadData(String query) {
        //this.activity = null;
        view.onFetchDataStarted();
        subscriptions.clear();
        Disposable disposable = restcalls.listVolumes(query, Constants.KEY).subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler).subscribe(response -> view.onFetchDataSuccess(response), error -> view.onFetchDataError(error));
        subscriptions.add(disposable);
    }
}
