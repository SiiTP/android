package com.dbtest.ivan.app.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
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
    private ReminderListAdapter mAdapter;
    private ReminderLoader mLoader;
    private OnItemSelectedListener mItemSelectedListener;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mItemSelectedListener.onReminderSelected(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new ReminderListAdapter(inflater.getContext(),
                R.layout.reminder_item, new ArrayList<Reminder>());
        setListAdapter(mAdapter);
//        mLoader = getLoaderManager().initLoader(ReminderLoader.LOADER_REMINDER_ID, null, this);


        mLoader = (ReminderLoader) getLoaderManager().initLoader(ReminderLoader.LOADER_REMINDER_ID, null, this);
        Log.d("myapp", "forceload");
        mLoader.forceLoad();
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
            mLoader = new ReminderLoader(this.getActivity());
        } else {
            Log.e("myapp", "incorrect id of loader by it's creation");
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Reminder>> loader, ArrayList<Reminder> data) {
        data.add(new Reminder("11.11.2012 10:11", "from code 1")); //todo delete this
        data.add(new Reminder("11.11.2011 11:11", "from code 2"));
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
