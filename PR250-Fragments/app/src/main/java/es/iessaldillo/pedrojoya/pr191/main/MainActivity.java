package es.iessaldillo.pedrojoya.pr191.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import es.iessaldillo.pedrojoya.pr191.R;
import es.iessaldillo.pedrojoya.pr191.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FAVOURITES = "TAG_FAVOURITES";
    private static final String TAG_CALENDAR = "TAG_CALENDAR";
    private static final String TAG_MUSIC = "TAG_MUSIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // Initially favourites option.
        if (savedInstanceState == null) {
            showFavorites();
        }
    }

    private void initViews() {
        ActivityCompat.requireViewById(this, R.id.btnFavourites).setOnClickListener(
                v -> showFavorites());
        ActivityCompat.requireViewById(this, R.id.btnCalendar).setOnClickListener(
                v -> showCalendar());
        ActivityCompat.requireViewById(this, R.id.btnMusic).setOnClickListener(v -> showMusic());
    }

    private void showFavorites() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_favorites)),
                TAG_FAVOURITES);
    }

    private void showCalendar() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_calendar)), TAG_CALENDAR);
    }

    private void showMusic() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_music)), TAG_MUSIC);
    }

}
