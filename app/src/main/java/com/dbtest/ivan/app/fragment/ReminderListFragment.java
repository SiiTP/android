package com.dbtest.ivan.app.fragment;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.adapter.ReminderListAdapter;
import com.dbtest.ivan.app.logic.db.entities.Reminder;
import com.dbtest.ivan.app.model.loader.ReminderLoader;

import java.util.ArrayList;

public class ReminderListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ArrayList<Reminder>> {
    private Reminder[] reminders = new Reminder[3];
    private ReminderListAdapter mAdapter;
    private ReminderLoader mLoader;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mItemSelectedListener.onReminderSelected(position);
    }

    OnItemSelectedListener mItemSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reminders[0] = new Reminder("17:11:1996 12:12", "description 2");
        reminders[1] = new Reminder("17:11:1995 11:11", "description 1");
        reminders[2] = new Reminder("17:11:1997 10:10", "description 3");
        mAdapter = new ReminderListAdapter(inflater.getContext(),
                R.layout.reminder_item, reminders);
        setListAdapter(mAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mItemSelectedListener = (OnItemSelectedListener) context; //TODO error if not implemented
    }

    @Override
    public Loader<ArrayList<Reminder>> onCreateLoader(int id, Bundle args) {
        ReminderLoader mLoader = null;
        if (id == ReminderLoader.LOADER_REMINDER_ID) {
            mLoader = new ReminderLoader(this.getContext());
        } else {
            Log.e("myapp", "incorrect id of loader by it's creation");
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Reminder>> loader, ArrayList<Reminder> data) {
        mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Reminder>> loader) {
        mAdapter.clear();
    }

    public interface OnItemSelectedListener {
        public void onReminderSelected(int position);
    }
}
