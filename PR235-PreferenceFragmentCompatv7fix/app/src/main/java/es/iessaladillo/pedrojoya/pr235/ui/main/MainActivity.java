package es.iessaladillo.pedrojoya.pr235.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Set;

import es.iessaladillo.pedrojoya.pr235.R;
import es.iessaladillo.pedrojoya.pr235.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences settings;
    private TextView lblSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        settings.registerOnSharedPreferenceChangeListener(this);
        showSettings();
    }

    private void initViews() {
        setupToolbar();
        setupFab();
        lblSettings = ActivityCompat.requireViewById(this, R.id.lblSettings);
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFab() {
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(v -> showSettingsActivity());
    }

    private void showSettingsActivity() {
        // No preferenceScreenKey initially.
        SettingsActivity.start(this, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuPreferencias:
                showSettingsActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        settings.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        showSettings();
    }

    private void showSettings() {
        StringBuilder sb = new StringBuilder("");
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
        Set<String> defaultShifts = new ArraySet<>(Arrays.asList(getResources().getStringArray(R.array.prefShifts_defaultValue)));
        Set<String> selectedShifts = settings.getStringSet(getString(R.string.prefShifts_key), defaultShifts);
        //noinspection ConstantConditions
        if (selectedShifts != null) {
            String[] shifts = new String[selectedShifts.size()];
            selectedShifts.toArray(shifts);
            for (String shift : shifts) {
                sb.append(shift).append("\n");
            }
        }
        sb.append(getString(R.string.prefCatchPhrase_key))
                .append(": ")
                .append(settings.getString(getString(R.string.prefCatchPhrase_key), ""))
                .append("\n");
        sb.append(getString(R.string.prefNetworkMode_key)).append(": ").append(
                settings.getBoolean(getString(R.string.prefNetworkMode_key), getResources()
                        .getBoolean(R.bool.prefNetworkMode_defaultValue))).append("\n");
        sb.append(getString(R.string.prefDifficulty_key)).append(": ").append(
                String.valueOf(settings.getInt(getString(R.string.prefDifficulty_key),
                        getResources().getInteger(R.integer.prefDifficulty_defaultValue))))
                .append("\n");
        lblSettings.setText(sb.toString());
    }

}
