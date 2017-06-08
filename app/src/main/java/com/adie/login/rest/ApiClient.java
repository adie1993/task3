package com.adie.login.rest;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    //baseurl
    public static final String BASE_URL = "https://private-abf69-adie2.apiary-mock.com";
    //inisialisasi retrofit
    private static Retrofit retrofit = null;
    //membuat penghubung untuk melakukan pemanggilan API
    public static Retrofit getClient() {
        if (retrofit==null) {

            OkHttpClient.Builder client = new OkHttpClient.Builder();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }
}
