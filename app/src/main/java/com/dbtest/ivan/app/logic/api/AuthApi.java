package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ivan on 27.03.16.
 */
public interface AuthApi {
    @POST("/auth")
    Call<User> register(@Body User user);

    @POST("/auth/login")
    Call<User> login(@Body User user);
}
