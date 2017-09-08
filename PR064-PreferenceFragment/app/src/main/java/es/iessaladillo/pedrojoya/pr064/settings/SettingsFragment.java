package es.iessaladillo.pedrojoya.pr064.settings;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;

import java.util.Set;

import es.iessaladillo.pedrojoya.pr064.R;

public class SettingsFragment extends PreferenceFragment implements
        OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
        initSummaries();
    }

    private void initSummaries() {
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            initSummary(getPreferenceScreen().getPreference(i));
        }
    }

    // Calls recursively if it's a PrefrenceScreen or PreferenceCategory.
    private void initSummary(Preference preference) {
        if (preference instanceof PreferenceScreen) {
            PreferenceScreen preferenceScreen = (PreferenceScreen) preference;
            for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
                initSummary(preferenceScreen.getPreference(i));
            }
        } else if (preference instanceof PreferenceCategory) {
            PreferenceCategory categoria = (PreferenceCategory) preference;
            for (int i = 0; i < categoria.getPreferenceCount(); i++) {
                initSummary(categoria.getPreference(i));
            }
        } else {
            updateSummary(preference);
        }

    }

    @Override
    public void onResume() {
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Find preference and update its summary.
        updateSummary(findPreference(key));
    }

    private void updateSummary(Preference preference) {
        if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(editTextPreference.getText());
        } else if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setSummary(listPreference.getEntry());
        } else if (preference instanceof MultiSelectListPreference) {
            MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) preference;
            Set<String> selectedValues = multiSelectListPreference.getValues();
            multiSelectListPreference.setSummary(selectedValues.toString());
        } else if (preference instanceof RingtonePreference) {
            RingtonePreference ringtonePreference = (RingtonePreference) preference;
            String tonePath = ringtonePreference.getSharedPreferences().getString(ringtonePreference.getKey(), "");
            Uri toneUri = Uri.parse(tonePath);
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), toneUri);
            String ringtoneTitle = ringtone.getTitle(getActivity());
            ringtonePreference.setSummary(ringtoneTitle);
            // Special hack for RingtonePreference.
            ringtonePreference.setOnPreferenceChangeListener((pref, newValue) -> {
                if (newValue != null && newValue instanceof String) {
                    String path = (String) newValue;
                    Ringtone tone = RingtoneManager.getRingtone(getActivity(),
                            Uri.parse(path));
                    pref.setSummary(tone.getTitle(getActivity()));
                }
                return true;
            });
        }
    }

}
