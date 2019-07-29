package com.airplay.webplus.Settings;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.airplay.webplus.R;

public class Settings extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    private final int PREFERENCE_DATA_SAVING = 1;
    private final int PREFERENCE_MAIN = 0;
    private int preferenceIdGiven = getIntent().getIntExtra("dataSaving", PREFERENCE_MAIN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        doOnCreate();
    }

    protected void doOnCreate(){
        processFragmentTransaction(preferenceIdGiven);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Boolean choose = getIntent().getBooleanExtra("choose", true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        return true;
    }

    private void processFragmentTransaction(int id){
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (id){
            case PREFERENCE_MAIN:
                fragmentTransaction.replace(R.id.settingsFragment, new SettingsFragment());
                break;
            case PREFERENCE_DATA_SAVING:
                fragmentTransaction.replace(R.id.settingsFragment, new DataSavingSettingsFragment());
                break;
        }
        fragmentTransaction.commit();
    }
}
