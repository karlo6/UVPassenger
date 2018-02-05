package com.example.christine.uvpassenger.Service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Christine on 2/5/2018.
 */

public class APIServiceGenerator {
    public static final String API_BASE_URL = "http://192.168.1.12/uvmonitor_api/public/api/";
    public static final String AUTHENTICATE_KEY = "Bearer 5a7175ef13599";
    public static String TOKEN;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader(" Authorization", TOKEN)
                        .build();
                return chain.proceed(request);
            });

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);

    }
}
