package com.habbat.bookable.retrofit;

/**
 * Created by HT-Moh on 15.12.17.
 *
 */

import android.util.MalformedJsonException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Converter factory for Retrofit
 */
public class StringConverterFactory extends Converter.Factory {

    @Override public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> converter =
                retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override public Object convert(ResponseBody responseBody) throws IOException {
                try {
                    return converter.convert(responseBody);
                } catch (MalformedJsonException | RuntimeException e) {
                    // do some logging.
                    throw e;
                }
            }
        };
    }
    @Override public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {

                @Override
                public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MediaType.parse("text/plain"), value);
                }
            };
        }
        return null;
    }
}