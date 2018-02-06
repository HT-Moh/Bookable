package com.habbat.bookable.presenters;

import com.habbat.bookable.models.Volumes;

/**
 * Bookable: Google book API
 * Created by Mohamed Habbat on 04.02.18.
 * www.habbat.ch Habbat C&D
 */

public interface MainContract {

    interface View {
        //Notify the view that a request is about to start.
        void onFetchDataStarted();
        //Notify the view that no more data will be returned.
        void onFetchDataCompleted();
        //return the requested data as its parameter,
        void onFetchDataSuccess(Volumes volumes);
        //Handle error
        void onFetchDataError(Throwable e);
    }

    interface Presenter {
        //Tell the presenter to start fetching data.
        void loadData(String query);
        //Notify the presenter that its view has become active. This can be used to trigger the API request.
        void subscribe(String query);
        //Notify the presenter that its view has become inactive. This can be used to cancel any previous request that hasn't returned yet.
        void unsubscribe();
        //notify the presenter that its view instance is about to be destroyed
        void onDestroy();
    }
}

