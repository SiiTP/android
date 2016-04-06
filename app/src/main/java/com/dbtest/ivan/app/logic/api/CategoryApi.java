package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Category;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ivan on 02.04.16.
 */
public interface CategoryApi {
    @POST("/category")
    Call<Category> create(@Body Category category);
}
