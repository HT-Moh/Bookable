package com.habbat.bookable.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.habbat.bookable.Constants;
import com.habbat.bookable.EndlessRecyclerOnScrollListener;
import com.habbat.bookable.R;
import com.habbat.bookable.RxBinding.RxFloatingSearchView;
import com.habbat.bookable.adapters.BooksAdapter;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.Volumes;
import com.habbat.bookable.retrofit.RetrofitNetworkServiceApi;
import com.marshalchen.ultimaterecyclerview.RecyclerItemClickListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.paperdb.Paper;
import io.reactivex.Observable;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

/**
 * Created by hackolos on 18.12.17.
 */

public class BooksRecycledListView extends BaseActivity implements BooksAdapter.OnItemLongClickListener {

    private static final String TAG = "BooksRecycledListView";


    @BindView(R.id.recyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.floating_search_view)
    public FloatingSearchView mSearchView;

    //Adapter
    private BooksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //@State(ListItemBunder.class)
    List<Item> items = null;
    //User key or Oauth token
    private Boolean isOAuth = false;

    //Setting for float search
    private static final String SETTINGS = "SETTINGS";
    private static final String DISABLE_SEARCH_PREVIEW = "DISABLE_SEARCH_PREVIEW";
    private Boolean isSearchPreviewDisabled = true;

    //Default search text
    private String defaultSearchText = "A";
    private int firstLetter= 65;
    private Boolean isSearch= false;

    //Inject API Service
    @Inject
    RetrofitNetworkServiceApi retrofitNetworkServiceApi;

    /***********************************************************
     * Managing Life Cycle
     **********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
       // Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.book_layout_recycle_view);
        ButterKnife.bind(this);
        defaultSearchText = "A";
        // To improve performance
        // Layout won't change
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, HORIZONTAL);
        mRecyclerView.addItemDecoration(itemDecor);

        isOAuth = getIntent().getBooleanExtra("OAUTH", false);

        //Call API
        //Reactive text update
        Observable<CharSequence> queryObservable = RxFloatingSearchView.queryChanges(mSearchView);
        queryObservable.doOnNext(query -> SearchViewOnQueryChange(query)).subscribe();


        mSearchView.setOnHomeActionClickListener(
                new FloatingSearchView.OnHomeActionClickListener() {
                    @Override
                    public void onHomeClicked(){
                        Log.d(TAG, "onHomeClicked()");
                    }

                });

        SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        isSearchPreviewDisabled = sp.getBoolean(DISABLE_SEARCH_PREVIEW,true);

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.action_settings) {

                    //Need to be moved to helper class
                    SharedPreferences sp = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    isSearchPreviewDisabled=!isSearchPreviewDisabled;
                    ed.putBoolean(DISABLE_SEARCH_PREVIEW,isSearchPreviewDisabled);
                    item.setTitle("Disable search preview");
                    if (isSearchPreviewDisabled){
                        item.setTitle("Enable search preview");
                    }
                    //Do it in background
                    ed.apply();

                } else {

                    //TODO further action
                }

            }
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(){
                if (firstLetter<=90){
                    firstLetter++;
                    Character nextLetter = (char)firstLetter++;
                    reactiveCall(Character.toString(nextLetter));
                }

            }

        });
        // Setup Card Clicks
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        // Send to Task View
                        Intent bookActivity = new Intent(BooksRecycledListView.this, BookActivity.class);
                        bookActivity.putExtra("BOOK",  Parcels.wrap(items.get(position)));
                        startActivity(bookActivity);
                        //overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
                    }
                })
        );
    }

    @Override protected void onResume(){
        super.onResume();
        if (items==null){
            items=  Paper.book().read("BOOKS");
        }
        if (items!=null){
            mAdapter = new BooksAdapter(items,this);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            reactiveCall(defaultSearchText);
        }
    }

    @Override public boolean onItemLongClicked(int position){
        if (items!=null &&  items.size()>position){
            Item item = items.get(position);
        }
        return true;
    }


    public void handleResponse(Volumes response){
        if (response!=null){
//            if (items==null){
//                items = new ArrayList<>();
//            }
           // if (isSearch ){
                items = response.items;
                isSearch=false;
           // }
//            else {
//                items.addAll(response.items);
//                items = items.stream().distinct().collect(Collectors.toList());
//            }

            runOnUiThread(new Runnable() {
                @Override public void run() {
                    mAdapter.setItems(items);

                }
            });

            //Quick dirty setting :-)
            if (!isSearchPreviewDisabled){
                CharSequence newSuggestions = "";
                List<SearchSuggestion> ss = new ArrayList<SearchSuggestion>();
                for( int i=0; i<items.size(); i++){
                    Item item = items.get(i);
                    int j=i;
                    ss.add(new SearchSuggestion() {
                        @Override
                        public String getBody() {
                            return item.volumeInfo.getTitle();
                        }

                        @Override
                        public int describeContents() {
                            return j;
                        }

                        @Override
                        public void writeToParcel(Parcel parcel, int i) {
                            //TODO Impement parcel
                        }
                    });
                }
                mSearchView.swapSuggestions(ss);
            }

        }
    }
    public void handleError(Throwable error){
        //TODO handle the error on the UI
        Log.e(TAG,error.getMessage());
    }
    private void SearchViewOnQueryChange(CharSequence query){
        if(query!=null && query.length()>0){
            isSearch = true;
            reactiveCall(query.toString());
        }
    }

    private void reactiveCall(String query){
        if (isOAuth) {
            //Reactive call
            retrofitNetworkServiceApi.call(Constants.ApiCalls.GETYOURVOLUMES, this, query);

        } else {
            //Reactive call
            retrofitNetworkServiceApi.call(Constants.ApiCalls.GETVOLUMES, this, query);

        }
    }

}
