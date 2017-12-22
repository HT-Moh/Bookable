package com.habbat.bookable.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
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
import de.cketti.mailto.EmailIntentBuilder;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
    @BindView(R.id.internetStatus)
    TextView internetStatus = null;
    //Items of books
    List<Item> items = null;
    //First alphabet
    private int firstLetter = 65;

    private int numberOfCalls = 0;

    //Inject API Service
    @Inject
    RetrofitNetworkServiceApi retrofitNetworkServiceApi;

    private Disposable networkDisposable;
    private Disposable internetDisposable;


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
        getVolumesBtn.setOnClickListener((View v) -> {
            startBookActivity(false);
        });

        getVolumesBtnOAuth.setOnClickListener((View v) -> {
            startLoginActivity();
        });

        txvResult.setMovementMethod(new ScrollingMovementMethod());
        items = Paper.book().read("BOOKS");
        if (items==null){
            for (int i=firstLetter; i<90;i++){
                reactiveCall(Character.toString((char)i));
            }
        }
        else {
            //Navigate to books after OAuth
            if(getIntent().getBooleanExtra("NAVIGATE_TO_BOOKS",false)){
                startBookActivity(true);
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

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    Log.d(TAG, connectivity.toString());
                    final NetworkInfo.State state = connectivity.getState();
                    final String name = connectivity.getTypeName();
                    internetStatus.setText(String.format("state: %s, typeName: %s", state, name));
                });

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> internetStatus.setText( isConnected?"Info":"No internet connection is available"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
        safelyDispose(networkDisposable, internetDisposable);

    }
    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
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
            //TODO
            //we copy the items to prevent thread lock
            Paper.book().write("BOOKS", new ArrayList<>(items));
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
    public void sendMail(View widget) {
        EmailIntentBuilder.from(this)
                .to("mohamedhabbat@icloud.com")
                .cc("mohamed@habbat.ch")
                .subject("Bookable App - Github")
                .body("Hello\n")
                .start();
    }
}
