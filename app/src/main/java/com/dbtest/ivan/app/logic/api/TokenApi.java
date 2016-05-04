package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ivan on 29.04.16.
 */
public interface TokenApi {
    @POST("/token")
    Call<Token> registerToken(@Body Token token);
}
