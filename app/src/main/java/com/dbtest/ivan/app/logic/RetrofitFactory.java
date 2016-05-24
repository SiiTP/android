package com.dbtest.ivan.app.logic;

import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.logic.db.entities.adapters.CategoryGsonAdapter;
import com.dbtest.ivan.app.logic.db.entities.adapters.FriendGsonAdapter;
import com.dbtest.ivan.app.logic.db.entities.adapters.ReminderGsonAdapter;
import com.dbtest.ivan.app.model.Friend;
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
    public static final String GENYMOTION_URI = "http://10.0.3.2:8080";
    public static final String LOCALHOST_URI = "http://localhost:8080";
    public static String BASE_URL = GENYMOTION_URI;//todo add from resources
    private static String JSESSION_ID = null;
    public static final String SESSION_COOKIE_NAME = "JSESSIONID";
    public static final String URI_NAME = "pref_server_ip";
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
        builder.registerTypeAdapter(Friend.class, new FriendGsonAdapter());

        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(builder.create())).client(okBuiler.build()).baseUrl(BASE_URL).build();
    }
    public static void setSession(String session){
        JSESSION_ID = session;
    }
    public static void setServerURI(String uri){
        BASE_URL = uri;
    }

    public static void main(String[] args) {
        String f = "e4oqGb2IHvM:APA91bGJgkjS0O8DbAGWMTd7UG4QsVzrhFOqPgaJy5c12w0lEnzbDoa0xdKADlK_QHYoUvJzRMl2ac-TaFCJwsVANCXOkI0rGrXYAJvKORhMeObg_G1FyQdVWD_9E4FnpgEFW_uoRBff";
        System.out.println(f.length());
    }
}
