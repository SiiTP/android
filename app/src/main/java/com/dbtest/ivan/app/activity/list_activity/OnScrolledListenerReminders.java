package com.dbtest.ivan.app.activity.list_activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ivan on 17.04.2016.
 */
public class OnScrolledListenerReminders extends RecyclerView.OnScrollListener {
    private FloatingActionButton buttonAdd;

    public OnScrolledListenerReminders(FloatingActionButton buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 3) {
            buttonAdd.hide();
        }
        if (dy < - 3) {
            buttonAdd.show();
        }
    }
}

