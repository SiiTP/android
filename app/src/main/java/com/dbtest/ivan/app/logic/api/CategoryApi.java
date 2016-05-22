package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @DELETE("/category?categoryName={categoryName}")
    Call<Category> delete(@Path("categoryName") String categoryName);
}
