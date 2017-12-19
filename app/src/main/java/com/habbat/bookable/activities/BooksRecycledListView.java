package com.habbat.bookable.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.habbat.bookable.Constants;
import com.habbat.bookable.R;
import com.habbat.bookable.adapters.BooksAdapter;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.Volumes;
import com.habbat.bookable.retrofit.AsyncResponseDelegate;
import com.habbat.bookable.retrofit.RetrofitNetworkServiceApi;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by hackolos on 18.12.17.
 */

public class BooksRecycledListView extends Activity implements AsyncResponseDelegate  {
    private static final String TAG = "BooksRecycledListView";
    @BindView(R.id.recyclerview)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Item> items = null;
    private Observer<Volumes> dataObserver = null;
    private Boolean isOAuth = false;
    @Inject
    RetrofitNetworkServiceApi retrofitNetworkServiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout_recycle_view);
        ButterKnife.bind(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        isOAuth = getIntent().getBooleanExtra("OAUTH",false);

        //Here to replaced by DI using dagger
        retrofitNetworkServiceApi = new RetrofitNetworkServiceApi();

        if (isOAuth){
            retrofitNetworkServiceApi.call(Constants.ApiCalls.GETYOURVOLUMES,this,this);

        }
        else {
            retrofitNetworkServiceApi.call(Constants.ApiCalls.GETVOLUMES,this,this);

        }
    }
    @Override protected void onResume(){
        super.onResume();
        if (items!=null){
            mAdapter = new BooksAdapter(items,this);
            mRecyclerView.setAdapter(mAdapter);
        }
        Log.e(TAG, "onCreate: Token available, just launch MainActivity");

    }

    @Override
    public void processFinish(Object output){
        Volumes vlms  = null;
        if (output!=null && output instanceof Volumes){
            items  = ((Volumes) output).items;
            mAdapter = new BooksAdapter(items,this);
            mRecyclerView.setAdapter(mAdapter);
        }
     //   if (vlms!=null){
            //Parcelable wrappedVlms = Parcels.wrap(vlms);
      //      Bundle bundle = new Bundle();
       //     bundle.putParcelable("volumes", Parcels.wrap(vlms));
        //}
    }
}
