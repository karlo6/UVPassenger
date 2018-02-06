package com.example.christine.uvpassenger.Service;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Christine on 2/5/2018.
 */

public class APIServiceGenerator {
    public static final String API_BASE_URL = "http://10.194.191.140/uvmonitor_api/public/api/";
    public static final String AUTHENTICATE_KEY = "Bearer 5a785d53b4a46";
    public static String TOKEN;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS);



    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", TOKEN)
                        .build();

                Response response = chain.proceed(request);
                if(response.code() == 401){
                    Log.e("HI", "HI");
                    return response;
                }
                return  response;
            }
        }).build()).build();
        return retrofit.create(serviceClass);

    }
}
