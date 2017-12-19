package com.habbat.bookable.retrofit;

import android.content.Context;
import android.util.Log;

import com.habbat.bookable.Constants;
import com.habbat.bookable.R;
import com.habbat.bookable.models.Item;
import com.habbat.bookable.models.Volumes;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hackolos on 18.12.17.
 */

public class RetrofitNetworkServiceApi implements RetrofitNetworkService{
    private static final String TAG = "RetrofitNetworkServiceApi";
    private Context context;
    private AsyncResponseDelegate delegate;
    @Inject
    public RetrofitNetworkServiceApi(){

    }
    @Override
    public void call(Constants.ApiCalls call, final AsyncResponseDelegate delegate, Context context) {
        this.context = context;
        this.delegate = delegate;
        switch (call){
            case OAUTH:

                break;
            case GETVOLUMES:
                getVolumes();
                break;

            case GETYOURVOLUMES:
                getVolumesOAuth();

                break;
        }

    }
    private void getVolumesOAuth(){
        OAuthServer server= RetrofitBuilder.getOAuthClient(context);
        Call<Volumes> volumes=server.listVolumes();
        volumes.enqueue(new Callback<Volumes>() {
            @Override
            public void onResponse(Call<Volumes> call, Response<Volumes> response) {
                Log.e(TAG,"The call listFilesCall succeed with [code="+response.code()+" and has body = "+response.body()+" and message = "+response.message()+" ]");
                //ok we have the list of files on GDrive
                if(response.code()==200&&response.body()!=null){
                    delegate.processFinish(response.body());
                    //txvResult.setText(response.body().toString());
                }else if(response.code()==400){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_400));
                }else if(response.code()==401){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_401));
                }else if(response.code()==403){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_403));
                }else if(response.code()==404){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_404));
                }
            }
            @Override
            public void onFailure(Call<Volumes> call, Throwable t) {
                Log.e(TAG,"The call listVolumes failed",t);
            }
        });
    }
    private void getVolumes(){
        OAuthServer server=RetrofitBuilder.getSimpleClient(context);
        Call<Volumes> listVolumes=server.listVolumes("flowers","AIzaSyAxsSeJtwoHPy1vRaZaIvKWYMT5SJvY7eE");
        listVolumes.enqueue(new Callback<Volumes>() {
            @Override
            public void onResponse(Call<Volumes> call, Response<Volumes> response) {

                Log.e(TAG,"The call listVolumes succeed with [code="+response.code()+" and has body = "+response.body()+" and message = "+response.message()+" ]");
                //ok we have the list of files on GDrive
                if(response.code()==200&&response.body()!=null){
                    //txvResult.setText(response.body().toString());
                    //List<Item> items = response.body().getItems();
                    delegate.processFinish(response.body());
                    //Log.e(TAG,"total" + items.size());
                }else if(response.code()==400){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_400));
                }else if(response.code()==401){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_401));
                }else if(response.code()==403){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_403));
                }else if(response.code()==404){
                    //txvResult.setText(response.message()+"\r\n"+getString(R.string.http_code_404));
                }
            }
            @Override
            public void onFailure(Call<Volumes> call, Throwable t) {
                Log.e(TAG,"The call listFilesCall failed",t);
            }
        });
    }
}
