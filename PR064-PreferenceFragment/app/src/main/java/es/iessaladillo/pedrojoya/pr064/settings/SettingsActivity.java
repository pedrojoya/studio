package es.iessaladillo.pedrojoya.pr064.settings;

import android.app.Activity;
import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr064.R;
import es.iessaladillo.pedrojoya.pr064.utils.FragmentUtils;

public class SettingsActivity extends Activity {

    private static final String TAG_SETTINGS_FRAGMENT = "TAG_SETTINGS_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadSettingsFragment();
    }

    private void loadSettingsFragment() {
        if (getFragmentManager().findFragmentById(R.id.flContent) == null) {
            FragmentUtils.replaceFragment(getFragmentManager(), R.id.flContent,
                    new SettingsFragment(), TAG_SETTINGS_FRAGMENT);
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

}
