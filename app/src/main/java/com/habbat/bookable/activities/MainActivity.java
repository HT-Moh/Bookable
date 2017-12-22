package com.habbat.bookable.activities;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.habbat.bookable.Constants;
import com.habbat.bookable.R;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.Volumes;
import com.habbat.bookable.retrofit.RetrofitNetworkServiceApi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.getVolumes)
    Button getVolumesBtn;
    @BindView(R.id.getYourVolumes)
    Button getVolumesBtnOAuth;
    @BindView(R.id.txvResult)
    TextView txvResult;
    @BindView(R.id.activity_main_ll)
    LinearLayout mainLL;
    AnimationDrawable animationDrawable = null;
    //Items of books
    List<Item> items = null;
    //First alphabet
    private int firstLetter = 65;

    private int numberOfCalls = 0;

    //Inject API Service
    @Inject
    RetrofitNetworkServiceApi retrofitNetworkServiceApi;
    /***********************************************************
     *  Managing LifeCycle
     **********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Paper.init(this);
        animationDrawable = (AnimationDrawable) mainLL.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        getVolumesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBookActivity(false);
            }
        });

        getVolumesBtnOAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });
        txvResult.setMovementMethod(new ScrollingMovementMethod());
        items = Paper.book().read("BOOKS");
        if (items==null){
            for (int i=firstLetter; i<90;i++){
                reactiveCall(Character.toString((char)i));
            }
        }


    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
    }

    @Override protected void onResume(){
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void startBookActivity(Boolean isOath){
        Intent booksActivity = new Intent(this,BooksRecycledListView.class);
        booksActivity.putExtra("OAUTH",isOath);
        startActivity(booksActivity);
    }
    void startLoginActivity(){
        Intent loginActivity = new Intent(this,LoginActivity.class);
        startActivity(loginActivity);
    }
    private void reactiveCall(String query){
        retrofitNetworkServiceApi.call(Constants.ApiCalls.GETVOLUMES, this, query);
    }
    public void handleResponse(Volumes response){
        if (response!=null){
            if (items==null){
                items = new ArrayList<>();
            }
            items.addAll(response.items);
            items = items.stream().distinct().collect(Collectors.toList());
            //if(numberOfCalls==90-65){
                Paper.book().write("BOOKS", items);
            //}
        }
        numberOfCalls++;
        Log.e(TAG, "Number of calls : " + numberOfCalls);

    }
    public void handleError(Throwable error){
        //TODO handle the error on the UI
        if(error!=null && error.getMessage()!=null){
            Log.e(TAG,error.getMessage());
        }
    }
}
