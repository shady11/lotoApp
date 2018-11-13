package com.example.rossoneri.loto.network;


import com.example.rossoneri.loto.entities.AccessToken;
import com.example.rossoneri.loto.entities.DrawResponse;
import com.example.rossoneri.loto.entities.DrawTicketResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("email") String email, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @GET("getDraws")
    Call<DrawResponse> draws();

    @GET("getDrawTickets")
    Call<DrawTicketResponse> drawTickets(@Query("draw_id") int draw_id);

}
