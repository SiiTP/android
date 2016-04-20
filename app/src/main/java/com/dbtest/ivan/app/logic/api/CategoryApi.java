package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by ivan on 02.04.16.
 */
public interface CategoryApi {
    @POST("/category")
    Call<Category> create(@Body Category category);

    @PUT("/category")
    Call<Category> update(@Body Category category);

    @GET("/category")
    Call<ArrayList<Category>> get();
}
