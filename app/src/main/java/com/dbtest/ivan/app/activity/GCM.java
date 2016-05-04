package com.dbtest.ivan.app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.services.intent.TokenRegisterIntentService;

import junit.framework.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class GCM extends AbstractToolbarActivity {
    public static final int MENU_POSITION = -1;
    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_gcm;
    }
    AsyncTask<Integer,Integer,Integer> task;
    @NonNull
    @Override
    public Integer getMenuPosition() {
        return MENU_POSITION;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                TT tt = new TT("Hello","a@a.a");
                Call<TT> ttCall = RetrofitFactory.getInstance().create(TestGCMApi.class).testGcm(tt);
                try {
                    ttCall.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Button getToken = (Button) findViewById(R.id.get_token);
        if (getToken != null) {
            getToken.setOnClickListener(v -> {
                Intent intent = new Intent(GCM.this, TokenRegisterIntentService.class);
                startService(intent);
            });
        }
        Button send_smth = (Button) findViewById(R.id.gcm_send_smth);
        if (send_smth != null) {
            send_smth.setOnClickListener(v -> {
                task.execute(1);



            });
        }

    }
    public class TT{
        private String msg;
        private String mail;
        public TT() {
        }

        public TT(String msg, String mail) {
            this.msg = msg;
            this.mail = mail;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }
    }
    public interface TestGCMApi{
        @POST("/test")
        Call<TT> testGcm(@Body TT tt);
    }
}
