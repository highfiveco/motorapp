package com.mirapharmacy.hifivy;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClass {

    @GET("get_data")
    Call<TokenBody> sendToken(
            @Query("id") String id,
            @Header("token") String token,
            @Query(value = "name") String name
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("username") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("insert_data")
    Call<UpdateData> updateData(
            @Header("token") String token,
            @Field("token") String token_body,
            @Field("read_id") String read_id,
            @Field("current_read") String current_read,
            @Field("last_read") String last_read

    );
}
