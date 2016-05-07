package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.model.Friend;
import com.dbtest.ivan.app.model.RemoveFriendResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by said on 10.04.16.
 */
public interface FriendApi {
    @GET("/friend")
    Call<List<Friend>> getFriends();

    @DELETE("/friend")
    Call<RemoveFriendResponse> removeFriend(@Query("friendEmail") String email);

    @POST("/friend")
    Call<Friend> inviteFriend(@Body Friend friend);
}
