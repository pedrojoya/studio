package es.iessaldillo.pedrojoya.pr191.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import es.iessaldillo.pedrojoya.pr191.R;
import es.iessaldillo.pedrojoya.pr191.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_CURRENT_ITEM_ID = "STATE_CURRENT_ITEM_ID";
    private static final String STATE_FAB_IS_HIDDEN = "STATE_FAB_IS_HIDDEN";

    private BottomNavigationView bottomNavigationView;

    @IdRes
    private int currentItemId = R.id.mnuCalendar;
    private boolean firstLoad;
    private boolean noReplace;
    private FloatingActionButton fab;
    private boolean isFabHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoresInstanceState(savedInstanceState);
        initViews();
        if (savedInstanceState == null) {
            firstLoad = true;
            @IdRes int defaultItem = R.id.mnuCalendar;
            // We simulate click on first option.
            bottomNavigationView.setSelectedItemId(defaultItem);
            // bottomNavigationView.findViewById(R.id.mnuFavorites).performClick();
        } else {
            noReplace = true;
        }
    }

    private void restoresInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentItemId = savedInstanceState.getInt(STATE_CURRENT_ITEM_ID, R.id.mnuCalendar);
            isFabHidden = savedInstanceState.getBoolean(STATE_FAB_IS_HIDDEN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_ITEM_ID, currentItemId);
        outState.putBoolean(STATE_FAB_IS_HIDDEN, (Boolean) fab.getTag());
    }

    private void initViews() {
        setSupportActionBar(ActivityCompat.requireViewById(this, R.id.toolbar));
        setupBottomNavigationView();
        setupFab();
    }

    private void setupFab() {
        fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setTag(isFabHidden);
        if (isFabHidden) {
            fab.setScaleX(0);
            fab.setScaleY(0);
        } else {
            fab.setScaleX(1);
            fab.setScaleY(1);
        }
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this, R.id.bottomNavigationView);

        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(
                currentItemId != R.id.mnuFavorites);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            showItem(item);
            currentItemId = item.getItemId();
            return true;
        });
    }

    private void showItem(MenuItem item) {
        if (!noReplace) {
            switch (item.getItemId()) {
                case R.id.mnuFavorites:
                    showOptionFragment(MainFragment.newInstance(R.id.mnuFavorites,
                            getString(R.string.main_activity_favorites)),
                            getString(R.string.main_activity_favorites), firstLoad);
                    // Disable music option.
                    bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(false);
                    break;
                case R.id.mnuCalendar:
                    showOptionFragment(MainFragment.newInstance(R.id.mnuCalendar,
                            getString(R.string.main_activity_calendar)),
                            getString(R.string.main_activity_calendar), firstLoad);
                    break;
                case R.id.mnuMusic:
                    showOptionFragment(MainFragment.newInstance(R.id.mnuMusic,
                            getString(R.string.main_activity_music)),
                            getString(R.string.main_activity_music), firstLoad);
                    break;
            }
            noReplace = true;
            firstLoad = false;
        } else {
            noReplace = false;
        }
    }

    private void showOptionFragment(Fragment fragment, String tag, boolean addToBackStack) {
        if (addToBackStack) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent, fragment,
                    tag);
        } else {
            FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flContent,
                    fragment, tag, tag, FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    @Override
    public void onBackPressed() {
        noReplace = true;
        super.onBackPressed();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof MainFragment) {
            ((MainFragment) fragment).setOnFragmentShownListener(menuItemResId ->
                bottomNavigationView.setSelectedItemId(menuItemResId));
        }
    }

}
