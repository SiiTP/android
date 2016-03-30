package com.dbtest.ivan.app.logic;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ivan on 26.03.16.
 */
public class RetrofitFactory {
    private static final String BASE_URL ="http://10.0.3.2:8080";//todo add from resources
    public static Retrofit getInstance(){
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
    }
}
