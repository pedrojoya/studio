package es.iessaladillo.pedrojoya.pr235.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr235.R;

public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    private static final String EXTRA_PREFERENCE_SCREEN_KEY = "EXTRA_PREFERENCE_SCREEN_KEY";
    private static final String TAG_PREFERENCE_FRAGMENT = "TAG_PREFERENCE_FRAGMENT";

    private String preferenceScreenKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        obtainIntentData();
        showPreferenceFragment();
    }

    private void initViews() {
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void obtainIntentData() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_PREFERENCE_SCREEN_KEY)) {
            preferenceScreenKey = getIntent().getStringExtra(EXTRA_PREFERENCE_SCREEN_KEY);
        }
    }

    private void showPreferenceFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCE_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                    SettingsFragment.newInstance(preferenceScreenKey)).commit();
        }
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat,
            PreferenceScreen preferenceScreen) {
        SettingsActivity.start(this, preferenceScreen.getKey());
        return true;
    }

    public static void start(Context context, String preferenceScreenKey) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(EXTRA_PREFERENCE_SCREEN_KEY, preferenceScreenKey);
        context.startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
