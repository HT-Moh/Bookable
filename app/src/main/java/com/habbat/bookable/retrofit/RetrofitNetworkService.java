package com.habbat.bookable.retrofit;

import android.content.Context;

import com.habbat.bookable.Constants;

import io.reactivex.disposables.Disposable;

/**
 * Created by hackolos on 18.12.17.
 */

public interface RetrofitNetworkService {
    Disposable call(Constants.ApiCalls call, Context context, String query);
}
