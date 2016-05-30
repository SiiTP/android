package com.dbtest.ivan.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.services.intent.FullSyncService;
import com.dbtest.ivan.app.services.intent.LogOutIntentService;

/**
 * Created by ivan on 19.04.16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference logout = findPreference(getString(R.string.log_out_pref));
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), LogOutIntentService.class);
                getActivity().startService(i);
                return false;
            }
        });
        Preference sync = findPreference(getString(R.string.pref_sync));
        sync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), FullSyncService.class);
                getActivity().startService(i);
                return false;
            }
        });
    }
}
