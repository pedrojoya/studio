package es.iessaldillo.pedrojoya.pr191;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FAVORITES = "TAG_FAVORITES";
    private static final String TAG_CALENDAR = "TAG_CALENDAR";
    private static final String TAG_MUSIC = "TAG_MUSIC";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

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
            return true;
        });
        // We simulate click on first option.
        bottomNavigationView.findViewById(R.id.mnuFavorites).performClick();
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
