package es.iessaladillo.pedrojoya.pr152.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr152.R;
import es.iessaladillo.pedrojoya.pr152.ui.settings.SettingsFragment;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private SharedPreferences settings;
    private TextView lblSettings;
    private SharedPreferences.OnSharedPreferenceChangeListener onSharePreferencesChangeListener;

    public MainFragment() {
    }

    static Fragment getInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        onSharePreferencesChangeListener = (sharedPreferences, key) -> showSettings();
        showSettings();

    }

    private void setupViews(View view) {
        lblSettings = ViewCompat.requireViewById(view, R.id.lblSettings);
        setupActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        settings.registerOnSharedPreferenceChangeListener(onSharePreferencesChangeListener);
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onPause() {
        settings.unregisterOnSharedPreferenceChangeListener(onSharePreferencesChangeListener);
        super.onPause();
    }

    private void showSettings() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.prefSync_key)).append(": ").append(
            settings.getBoolean(getString(R.string.prefSync_key),
                getResources().getBoolean(R.bool.prefSync_defaultValue))).append("\n");
        sb.append(getString(R.string.prefConnectionType_key)).append(": ").append(
            settings.getString(getString(R.string.prefConnectionType_key),
                getString(R.string.prefConnectionType_defaultValue))).append("\n");
        sb.append(getString(R.string.prefLanguage_key)).append(": ").append(
            settings.getString(getString(R.string.prefLanguage_key),
                getString(R.string.prefLanguage_defaultValue))).append("\n");
        sb.append(getString(R.string.prefBigFontSize_key)).append(": ").append(
            settings.getBoolean(getString(R.string.prefBigFontSize_key),
                getResources().getBoolean(R.bool.prefBigFontSize_defaultValue))).append("\n");
        sb.append(getString(R.string.prefShifts_key)).append(":\n");
        Set<String> defaultShifts = new ArraySet<>(
            Arrays.asList(getResources().getStringArray(R.array.prefShifts_defaultValue)));
        Set<String> selectedShifts = settings.getStringSet(getString(R.string.prefShifts_key),
            defaultShifts);
        //noinspection ConstantConditions
        if (selectedShifts != null) {
            String[] shifts = new String[selectedShifts.size()];
            selectedShifts.toArray(shifts);
            for (String shift : shifts) {
                sb.append(shift).append("\n");
            }
        }
        sb.append(getString(R.string.prefCatchPhrase_key)).append(": ").append(
            settings.getString(getString(R.string.prefCatchPhrase_key), "")).append("\n");
        sb.append(getString(R.string.prefNetworkMode_key)).append(": ").append(
            settings.getBoolean(getString(R.string.prefNetworkMode_key),
                getResources().getBoolean(R.bool.prefNetworkMode_defaultValue))).append("\n");
        sb.append(getString(R.string.prefDifficulty_key)).append(": ").append(String.valueOf(
            settings.getInt(getString(R.string.prefDifficulty_key),
                getResources().getInteger(R.integer.prefDifficulty_defaultValue)))).append("\n");
        lblSettings.setText(sb.toString());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSettings:
                showSettingsFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsFragment() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.flContent,
                SettingsFragment.newInstance(), SettingsFragment.class.getSimpleName())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(SettingsFragment.class.getSimpleName())
            .commit();
    }

}
