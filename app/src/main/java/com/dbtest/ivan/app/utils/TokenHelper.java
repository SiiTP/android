package com.dbtest.ivan.app.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by ivan on 27.04.16.
 */
public class TokenHelper {
    @Nullable
    public static String getToken(Context context,String authEntity){
        InstanceID id = InstanceID.getInstance(context);
        String t = null;
        try {
            t = id.getToken(authEntity, GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.d("myapp token", t);//secure or save in shared preferences?
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
