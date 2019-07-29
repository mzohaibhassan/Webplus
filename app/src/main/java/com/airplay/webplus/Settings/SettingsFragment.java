package com.airplay.webplus.Settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.airplay.webplus.R;
import com.airplay.webplus.SearchInstance;

public class SettingsFragment extends PreferenceFragmentCompat {
    private ListPreference searchEngineSelect;
    private SearchInstance instance = new SearchInstance();
    private Preference preference;

    private final Intent intent = new Intent(getContext(), Settings.class);

    private final int DATA_SAVING = 1;

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        setPreferencesFromResource(R.xml.preference, key);
        String[] searchEngineList = instance.getSearchEngines();
        String[] searchEngineLinkList = instance.getSearchEngineLinks();
        searchEngineSelect = (ListPreference) findPreference("defaultEngine");
        searchEngineSelect.setEntries(searchEngineList);
        searchEngineSelect.setEntryValues(searchEngineLinkList);

        preference = findPreference("dataSaving");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                intent.putExtra("preference", DATA_SAVING);
                startActivity(intent);
                return false; }
        });
    }

}
