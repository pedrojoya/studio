package es.iessaldillo.pedrojoya.pr191;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FAVORITES = "TAG_FAVORITES";
    private static final String TAG_CALENDAR = "TAG_CALENDAR";
    private static final String TAG_MUSIC = "TAG_MUSIC";
    private static final String STATE_CURRENT_ITEM_ID = "STATE_CURRENT_ITEM_ID";

    private BottomNavigationView bottomNavigationView;

    @IdRes private int currentItemId = R.id.mnuCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoresInstanceState(savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            // We simulate click on first option.
            bottomNavigationView.setSelectedItemId(R.id.mnuCalendar);
            // bottomNavigationView.findViewById(R.id.mnuFavorites).performClick();
        }
    }

    private void restoresInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentItemId = savedInstanceState.getInt(STATE_CURRENT_ITEM_ID, R.id.mnuCalendar);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_ITEM_ID, currentItemId);
    }

    private void initViews() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this, R.id.bottomNavigationView);

        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(currentItemId !=
                R.id.mnuFavorites);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mnuFavorites:
                    showFavorites();
                    break;
                case R.id.mnuCalendar:
                    showCalendar();
                    break;
                case R.id.mnuMusic:
                    showMusic();
                    break;
            }
            currentItemId = item.getItemId();
            return true;
        });
    }

    private void showFavorites() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_favorites)),
                TAG_FAVORITES).commitNow();
        // Disable music option.
        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(false);
    }

    private void showCalendar() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent,
                        MainFragment.newInstance(getString(R.string.main_activity_calendar)),
                        TAG_CALENDAR)
                .commitNow();
        // Enable music option.
        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(true);
    }

    private void showMusic() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent,
                        MainFragment.newInstance(getString(R.string.main_activity_music)),
                        TAG_MUSIC)
                .commitNow();
    }

}
