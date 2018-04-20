package es.iessaladillo.pedrojoya.pr233.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr233.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // if just lunched, select default menu item in drawer.
        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }
    }

    private void initViews() {
        setupToolbar();
        setupNavigationDrawer();

    }

    private void setupToolbar() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        setupBottomNavigationView();
    }

    private void setupNavigationDrawer() {
        drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this, R.id.bottomNavigationView);
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
            clearSelection(navigationView);
            return true;
        });
    }

    private void clearSelection(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        for (int i= 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
    }

    private void showFavorites() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_favorites))).commitNow();
        // Disable music option.
        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(false);
    }

    private void showCalendar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_calendar))).commitNow();
        // Enable music option.
        bottomNavigationView.getMenu().findItem(R.id.mnuMusic).setEnabled(true);
    }

    private void showMusic() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                MainFragment.newInstance(getString(R.string.main_activity_music))).commitNow();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuOption1:
            case R.id.mnuOption2:
            case R.id.mnuOption3:
            case R.id.mnuOption4:
                replaceFragment(item.getTitle().toString());
                item.setChecked(true);
                clearSelection(bottomNavigationView);
                break;
            case R.id.mnuOption5:
                showOption5Activity();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showOption5Activity() {
        Toast.makeText(this, R.string.main_activity_show_option5, Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                MainFragment.newInstance(title)).commit();
    }

    @SuppressLint("RestrictedApi")
    @SuppressWarnings("WeakerAccess")
    public static void clearSelection(BottomNavigationView view) {
        final BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            item.setChecked(false);
        }
    }

}
