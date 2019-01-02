package es.iessaldillo.pedrojoya.pr191.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.pedrojoya.pr191.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, MainActivityViewModelFactory.getInstance()).get(
            MainActivityViewModel.class);
        setupViews();
        observeCurrentOption();
        if (savedInstanceState == null) {
            navigateToStartFragment();
        }
    }

    private void observeCurrentOption() {
        viewModel.getCurrentOption().observe(this,
            option -> bottomNavigationView.getMenu().findItem(option).setChecked(true));
    }

    private void navigateToStartFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, MainFragment.newInstance(R.id.mnuFavorites, R.drawable.ic_favorite_black_24dp,
                getString(R.string.main_activity_favorites)))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

    private void setupViews() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this, R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            navigateToOption(item);
            return true;
        });
    }

    private void navigateToOption(MenuItem item) {
        Integer currentOption = viewModel.getCurrentOption().getValue();
        if (currentOption == null || currentOption != item.getItemId()) {
            switch (item.getItemId()) {
                case R.id.mnuFavorites:
                    replaceFragment(MainFragment.newInstance(R.id.mnuFavorites, R.drawable.ic_favorite_black_24dp,
                        getString(R.string.main_activity_favorites)), getString(R.string.main_activity_favorites));
                    break;
                case R.id.mnuCalendar:
                    replaceFragment(MainFragment.newInstance(R.id.mnuCalendar, R.drawable.ic_access_time_black_24dp,
                        getString(R.string.main_activity_calendar)), getString(R.string.main_activity_calendar));
                    break;
                case R.id.mnuMusic:
                    replaceFragment(MainFragment.newInstance(R.id.mnuMusic, R.drawable.ic_audiotrack_black_24dp,
                        getString(R.string.main_activity_music)), getString(R.string.main_activity_music));
                    break;
            }
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, fragment, tag)
            .addToBackStack(tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

}
