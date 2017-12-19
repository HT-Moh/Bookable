package com.habbat.bookable.retrofit;

import android.content.Context;

import com.habbat.bookable.Constants;

/**
 * Created by hackolos on 18.12.17.
 */

public interface RetrofitNetworkService {
    void call(Constants.ApiCalls call, final AsyncResponseDelegate delegate, Context context);
}
