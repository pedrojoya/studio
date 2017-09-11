package es.iessaladillo.pedrojoya.pr212.ui.main;

import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr212.R;
import es.iessaladillo.pedrojoya.pr212.base.AppCompatLifecycleActivity;
import es.iessaladillo.pedrojoya.pr212.utils.FragmentUtils;

public class MainActivity extends AppCompatLifecycleActivity {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        loadFragment();
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void loadFragment() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT);
        }
    }

}
