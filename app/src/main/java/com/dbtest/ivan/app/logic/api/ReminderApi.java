package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.logic.db.entities.Reminder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by ivan on 02.04.16.
 */
public interface ReminderApi {
    @POST("/reminder")
    Call<Reminder> create(@Body Reminder reminder);

    @PUT("/reminder")
    Call<Reminder> update(@Body Reminder reminder);


    @GET("/reminder")
    Call<ArrayList<Reminder>> get();
}
