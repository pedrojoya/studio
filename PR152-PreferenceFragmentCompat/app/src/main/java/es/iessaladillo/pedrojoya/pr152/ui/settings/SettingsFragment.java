package es.iessaladillo.pedrojoya.pr152.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import es.iessaladillo.pedrojoya.pr152.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
    private SharedPreferences.OnSharedPreferenceChangeListener onMultiListChangeListener;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        setPreferencesFromResource(R.xml.settings, key);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupActionBar();

        // TODO: It should update summary automatically but it doesn't
        //setupMultiListSummaryProvider();
        onMultiListChangeListener = this::updateMultiListSummary;
        updateMultiListSummary(getPreferenceScreen().getSharedPreferences(),
            getString(R.string.prefShifts_key));
        // Initial state
        updateIcons();
    }

    @SuppressWarnings("unused")
    private void setupMultiListSummaryProvider() {
        EditTextPreference.SimpleSummaryProvider.getInstance();
        Preference prefShifts = findPreference(getString(R.string.prefShifts_key));
        prefShifts.setSummaryProvider(
            (Preference.SummaryProvider<MultiSelectListPreference>) preference -> preference.getValues()
                .toString());
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.settings_title));
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
            onMultiListChangeListener);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
            onSharedPreferenceChangeListener);
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
            onMultiListChangeListener);
        super.onPause();
    }

    private void updateIcons() {
        updateIcon(getPreferenceScreen().getSharedPreferences(), getString(R.string.prefSync_key));
        updateIcon(getPreferenceScreen().getSharedPreferences(), getString(R.string.prefConnectionType_key));
    }

    private void updateIcon(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (TextUtils.equals(preference.getKey(), getString(R.string.prefSync_key))) {
            preference.setIcon(sharedPreferences.getBoolean(getString(R.string.prefSync_key),
                getResources().getBoolean(
                    R.bool.prefSync_defaultValue)) ? R.drawable.ic_sync_black_24dp : R.drawable.ic_sync_disabled_black_24dp);
        } else if (TextUtils.equals(preference.getKey(),
            getString(R.string.prefConnectionType_key))) {
            preference.setIcon(TextUtils.equals(
                sharedPreferences.getString(getString(R.string.prefConnectionType_key),
                    getString(R.string.prefConnectionType_defaultValue)),
                "wifi") ? R.drawable.ic_network_wifi_black_24dp : R.drawable.ic_signal_cellular_3_bar_black_24dp);
        }
    }

    @SuppressWarnings("unused")
    private void updateMultiListSummary(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.prefShifts_key))) {
            MultiSelectListPreference preference = findPreference(getString(R.string.prefShifts_key));
            String summary = preference.getValues().isEmpty() ? getString(
                R.string.prefShifts_summary) : preference.getValues().toString();
            preference.setSummary(summary);
        }
    }

}
