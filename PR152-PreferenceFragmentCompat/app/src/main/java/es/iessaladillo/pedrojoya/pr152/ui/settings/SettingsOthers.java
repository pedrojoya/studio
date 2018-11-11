package es.iessaladillo.pedrojoya.pr152.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import es.iessaladillo.pedrojoya.pr152.R;

public class SettingsOthers extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        setPreferencesFromResource(R.xml.settings_others, key);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupActionBar();
        onSharedPreferenceChangeListener = this::updateIcon;
        // Set icons according to current settings.
        updateIcons();
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.prefOthers_title));
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener);
        super.onPause();
    }

    private void updateIcons() {
        updateIcon(getPreferenceScreen().getSharedPreferences(), getString(R.string.prefNetworkMode_key));
        updateIcon(getPreferenceScreen().getSharedPreferences(), getString(R.string.prefDifficulty_key));
    }

    private void updateIcon(SharedPreferences sharedPreferences, String key) {
        int[] gaugeResIds = new int[] {
            R.drawable.ic_gauge_empty_black_24dp, R.drawable.ic_gauge_low_black_24dp,
            R.drawable.ic_gauge_high_black_24dp, R.drawable.ic_gauge_full_black_24dp};
        Preference preference = findPreference(key);
        if (TextUtils.equals(preference.getKey(), getString(R.string.prefNetworkMode_key))) {
            preference.setIcon(sharedPreferences.getBoolean(getString(R.string.prefNetworkMode_key),
                getResources().getBoolean(
                    R.bool.prefNetworkMode_defaultValue)) ? R.drawable.ic_signal_wifi_4_bar_black_24dp : R.drawable.ic_signal_wifi_off_black_24dp);
        } else if (TextUtils.equals(preference.getKey(), getString(R.string.prefDifficulty_key))) {
            int difficulty = sharedPreferences.getInt(getString(R.string.prefDifficulty_key),
                getResources().getInteger(R.integer.prefDifficulty_defaultValue));
            // Difficulty 1-4
            preference.setIcon(gaugeResIds[difficulty-1]);
        }
    }

}
