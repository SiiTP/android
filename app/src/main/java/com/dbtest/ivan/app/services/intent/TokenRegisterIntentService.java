package com.dbtest.ivan.app.services.intent;

import android.app.IntentService;
import android.content.Intent;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.RetrofitFactory;
import com.dbtest.ivan.app.logic.api.TokenApi;
import com.dbtest.ivan.app.logic.db.entities.Token;
import com.dbtest.ivan.app.utils.TokenHelper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ivan on 26.04.16.
 */
public class TokenRegisterIntentService extends IntentService {

    public TokenRegisterIntentService() {
        super("Token register intent service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = TokenHelper.getToken(getApplicationContext(),getString(R.string.proj_numb));
        Token tokenObj = new Token(token);
        Call<Token> tokenCall = RetrofitFactory.getInstance().create(TokenApi.class).registerToken(tokenObj);
        try {
            Response<Token> tokenResponse = tokenCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
