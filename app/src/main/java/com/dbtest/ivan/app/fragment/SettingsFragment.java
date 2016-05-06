package com.dbtest.ivan.app.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dbtest.ivan.app.R;

/**
 * Created by ivan on 19.04.16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
