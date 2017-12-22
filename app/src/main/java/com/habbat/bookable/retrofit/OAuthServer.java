package com.habbat.bookable.retrofit;
import com.habbat.bookable.models.Volumes;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hackolos on 15.12.17.
 */

public interface OAuthServer {
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> requestTokenForm(
            @Field("code")String code,
            @Field("client_id")String client_id,
            @Field("redirect_uri")String redirect_uri,
            @Field("grant_type")String grant_type);

    @FormUrlEncoded
    @POST("oauth2/v4/token")
    Call<OAuthToken> refreshTokenForm(
            @Field("refresh_token")String refresh_token,
            @Field("client_id")String client_id,
            @Field("grant_type")String grant_type);

    @GET("books/v1/volumes")
    Observable<Volumes> listVolumes(@Query("q")String q,
                                    @Query("key")String key);
    @GET("books/v1/volumes")
    Observable<Volumes> listVolumes(@Query("q")String q);

    @GET("books/v1/volumes")
    Flowable<Volumes> listVolumesF(@Query("q")String q,
                                  @Query("key")String key);
    @GET("books/v1/volumes")
    Flowable<Volumes> listVolumesF(@Query("q")String q);
}