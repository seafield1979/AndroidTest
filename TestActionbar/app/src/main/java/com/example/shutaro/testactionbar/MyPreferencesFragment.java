package com.example.shutaro.testactionbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;

/**
 * Created by shutaro on 2016/10/04.
 */
public class MyPreferencesFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key)
    {
        if (key.equals("background")) {
            ListPreference lp = (ListPreference) getPreferenceScreen().findPreference("background");
            Log.v("myLog", lp.getValue());
        } else if (key.equals("edittext_key")) {
            EditTextPreference ep = (EditTextPreference) getPreferenceScreen()
                    .findPreference("edittext_key");
            ep.setSummary(ep.getText());
            Log.v("myLog", ep.getText());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}