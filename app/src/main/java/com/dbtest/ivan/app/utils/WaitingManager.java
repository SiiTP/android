package com.dbtest.ivan.app.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dbtest.ivan.app.R;

/**
 * Created by ivan on 18.04.16.
 */
public class WaitingManager {
    public static void makeWaitingButton(Context context, Button button,boolean isWaiting){
        if(isWaiting){
            button.setTextColor(Color.GRAY);
        }else{
            button.setTextColor(ContextCompat.getColor(context, R.color.app_accent));
        }
        button.setEnabled(!isWaiting);
        button.setFocusableInTouchMode(!isWaiting);
    }
    public static void makeWaitingView(View editText,boolean isWaiting){
        editText.setEnabled(!isWaiting);
        editText.setFocusableInTouchMode(!isWaiting);
    }
    public static void makeWaitingProgressBar(Context context,ProgressBar bar,boolean isWaiting){
        if(isWaiting){
            bar.setVisibility(View.VISIBLE);
            bar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        }else{
            bar.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_out));
            bar.setVisibility(View.GONE);
        }
    }
}
