package com.circular.circular.utils;

import com.circular.circular.Constant;
import com.circular.circular.remote.ApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceProvider {

    private static RetrofitInstanceProvider _instance = null;

    private final ApiService mApiService;

    public RetrofitInstanceProvider() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(loggingInterceptor);

        OkHttpClient client = okHttpBuilder.build();

        final Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    public static RetrofitInstanceProvider getInstance() {
        if (_instance == null) {
            _instance = new RetrofitInstanceProvider();
        }
        return _instance;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
