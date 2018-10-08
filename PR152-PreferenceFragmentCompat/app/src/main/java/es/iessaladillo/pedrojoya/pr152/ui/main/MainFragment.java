package es.iessaladillo.pedrojoya.pr152.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr152.R;
import es.iessaladillo.pedrojoya.pr152.ui.settings.SettingsActivity;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment implements SharedPreferences
        .OnSharedPreferenceChangeListener {

    private SharedPreferences settings;
    private TextView lblSettings;

    public MainFragment() {
    }

    static Fragment getInstance() {
        return new MainFragment();
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
        initViews(getView());
        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        settings.registerOnSharedPreferenceChangeListener(this);
        showSettings();

    }

    private void initViews(View view) {
        setupToolbar(view);
        setupFab(view);
        lblSettings = ViewCompat.requireViewById(view, R.id.lblSettings);
    }

    private void setupToolbar(View view) {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(
                ViewCompat.requireViewById(view, R.id.toolbar));
    }

    private void setupFab(View view) {
        ViewCompat.requireViewById(view, R.id.fab).setOnClickListener(v -> showSettingsActivity());
    }

    private void showSettingsActivity() {
        // No preferenceScreenKey initially.
        SettingsActivity.start(requireActivity(), null);
    }


    @Override
    public void onDestroy() {
        settings.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        showSettings();
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
                        getResources().getBoolean(R.bool.prefBigFontSize_defaultValue))).append(
                "\n");
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
                        getResources().getBoolean(R.bool.prefNetworkMode_defaultValue))).append(
                "\n");
        sb.append(getString(R.string.prefDifficulty_key)).append(": ").append(String.valueOf(
                settings.getInt(getString(R.string.prefDifficulty_key),
                        getResources().getInteger(R.integer.prefDifficulty_defaultValue)))).append(
                "\n");
        lblSettings.setText(sb.toString());
    }

}
