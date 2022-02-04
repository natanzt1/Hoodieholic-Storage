package com.example.natan.storagehoodieholic.api;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.example.natan.storagehoodieholic.helper.preferenceHelper;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static ApiService getService(Context context) {
        final preferenceHelper preference = new preferenceHelper(context);
        OkHttpClient client = new OkHttpClient.Builder()
//                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request;
                        request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Context-Type", "application/json")
                                .addHeader("Authorization", "Bearer " + preference.getToken())
                                .build();
                        return chain.proceed(request);
                    }
                }).build();


//        OkHttpClient client=new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request=chain
//                                    .request()
//                                    .newBuilder()
//                                    .addHeader("Content-Type","application/json")
//                                    .addHeader("Authorization","Bearer "+"User_Preferance")
//                                    .build();
//
//                        return chain.proceed(request);
//                    }
//                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.86:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiService.class);
    }
}
