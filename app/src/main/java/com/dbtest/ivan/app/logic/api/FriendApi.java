package com.dbtest.ivan.app.logic.api;

import com.dbtest.ivan.app.model.Friend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by said on 10.04.16.
 */
public interface FriendApi {
    @GET("/friend")
    Call<List<Friend>> getFriends();
}
