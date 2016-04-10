package com.dbtest.ivan.app.logic;

import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.logic.db.entities.adapters.CategoryGsonAdapter;
import com.dbtest.ivan.app.logic.db.entities.adapters.ReminderGsonAdapter;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ivan on 26.03.16.
 */
public class RetrofitFactory {
    private static final String BASE_URL ="http://10.0.3.2:8080";//todo add from resources
    private static String JSESSION_ID = null;
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
    public static final String SESSION_STORAGE_NAME = "sessionStorage";
    public static Retrofit getInstance(){
        OkHttpClient.Builder okBuiler = new OkHttpClient.Builder();
        okBuiler.readTimeout(10, TimeUnit.SECONDS);
        okBuiler.connectTimeout(5, TimeUnit.SECONDS);
        okBuiler.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (JSESSION_ID != null) {
                    request = request.newBuilder().addHeader("Cookie", SESSION_COOKIE_NAME + '=' + JSESSION_ID).build();
                }
                return chain.proceed(request);
            }
        });
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Reminder.class, new ReminderGsonAdapter());
        builder.registerTypeAdapter(Category.class, new CategoryGsonAdapter());
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(builder.create())).client(okBuiler.build()).baseUrl(BASE_URL).build();
    }
    public static void setSession(String session){
        JSESSION_ID = session;
    }
}
