package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.model.RemoveCategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

    @DELETE("/category")
    Call<RemoveCategoryResponse> delete(@Query("categoryName") String categoryName);
}
