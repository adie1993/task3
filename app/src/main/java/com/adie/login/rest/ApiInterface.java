package com.adie.login.rest;

import com.adie.login.model.UsersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    //interface yang berperan penting untuk melakukan pemanggilan data pada endpoint API
    @FormUrlEncoded
    @POST("/login")
    Call<UsersResponse>Login(@Field("email") String email, @Field("password") String pass);
}
