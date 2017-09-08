package es.iessaladillo.pedrojoya.pr064.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Set;

import es.iessaladillo.pedrojoya.pr064.R;
import es.iessaladillo.pedrojoya.pr064.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private TextView lblSettings;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        initViews();
    }

    private void initViews() {
        lblSettings = this.findViewById(R.id.lblSettings);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        if (preferences != null) {
            showSettings();
        }
        super.onResume();
    }

    private void showSettings() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.pref_synchronize)).append(": ").append(
                preferences.getBoolean("prefSynchronize", false)).append("\n");
        sb.append(getString(R.string.pref_connection_type)).append(": ").append(
                preferences.getString("prefConnectionType",
                        getString(R.string.pref_connection_type_default))).append("\n");
        sb.append(getString(R.string.pref_big_letters)).append(": ").append(
                preferences.getBoolean("prefBigLetters", false)).append("\n");
        sb.append(getString(R.string.pref_shifts)).append(":\n");
        Set<String> selectedShifts = preferences.getStringSet("prefShifts", null);
        if (selectedShifts != null) {
            String[] shifts = new String[selectedShifts.size()];
            selectedShifts.toArray(shifts);
            for (String turno : shifts) {
                sb.append(turno).append("\n");
            }
        }
        sb.append(getString(R.string.pref_catchphrase)).append(": ").append(
                preferences.getString("prefCatchphrase", "")).append("\n");
        sb.append(getString(R.string.pref_notification_tone)).append(": ");
        String tonePath = preferences.getString("prefNotificationTone", "");
        Uri toneUri = Uri.parse(tonePath);
        Ringtone ringtone = RingtoneManager.getRingtone(this, toneUri);
        String ringtoneName = ringtone.getTitle(this);
        sb.append(ringtoneName).append("\n");
        sb.append(getString(R.string.pref_network)).append(": ").append(
                preferences.getBoolean("prefNetwork", false)).append("\n");
        lblSettings.setText(sb.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSettings:
                showSettingsActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsActivity() {
        this.startActivity(new Intent(this, SettingsActivity.class));
    }

}
