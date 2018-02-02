package com.habbat.bookable.retrofit;

import android.content.Context;

import com.habbat.bookable.Constants;
import com.habbat.bookable.activities.BooksRecycledListView;
import com.habbat.bookable.activities.HandleApiResponses;
import com.habbat.bookable.activities.MainActivity;
import com.habbat.bookable.models.Volumes;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by HT-Moh on 18.12.17.
 */

public class RetrofitNetworkServiceApi implements RetrofitNetworkService{
    private static final String TAG = "RetrofitNetworkServiceApi";
    private Context context;

    /**
     * Constructor using DI
     *
     */
    @Inject
    public RetrofitNetworkServiceApi(){

    }

    /**
     * Handle the call type
     * @param  call call type
     * @param  context activity context
     * @param  query search text for book
     */
    @Override
    public Disposable call(Constants.ApiCalls call, Context context, String query) {
        this.context = context;
        switch (call){
            case OAUTH:
                break;
            case GETVOLUMES:
                return getVolumes(query);

            case GETYOURVOLUMES:
               return getVolumesOAuth(query);
        }
        return null;
    }

    /**
     * Call API volumes using reactive and OAuth2
     * The query argument must specify an absolute {@link String}.
     * <p>
     *
     * @param  query search text for book
     */
    private Disposable getVolumesOAuth(String query){
        RestCalls server= RetrofitBuilder.getOAuthClient(context);
        Observable<Volumes> volumes = server.listVolumes(query);
        return volumes.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                //.delay(2,TimeUnit.SECONDS)
                .subscribe(response -> ((HandleApiResponses)context).handleResponse(response), error -> ((MainActivity)context).handleError(error));
    }
    /**
     * Call API volumes using reactive and key
     * The query argument must specify an absolute {@link String}.
     * <p>
     *
     * @param  query search text for book
     */
    private Disposable getVolumes(String query){
       RestCalls server= RetrofitBuilder.getSimpleClient(context,true);
       Observable<Volumes> volumes = server.listVolumes(query,Constants.KEY);
        return volumes.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                //  .delay(5,TimeUnit.SECONDS)
                .subscribe(response -> ((HandleApiResponses)context).handleResponse(response), error -> ((BooksRecycledListView)context).handleError(error));
    }
}
