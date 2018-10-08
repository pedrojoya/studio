package es.iessaladillo.pedrojoya.pr105.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnFragmentShownListener;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;
import es.iessaladillo.pedrojoya.pr105.ui.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr105.ui.main.option1.Option1Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option2.Option2Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option3.Option3Fragment;
import es.iessaladillo.pedrojoya.pr105.utils.FragmentUtils;
import es.iessaladillo.pedrojoya.pr105.utils.PicassoUtils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnToolbarAvailableListener, OnFragmentShownListener {

    private static final String PREFERENCES_FILE = "prefs";
    private static final String PREF_NAV_DRAWER_OPENED = "navdrawerOpened";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView imgProfile;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        // if just lunched, select default menu item in drawer.
        if (savedInstanceState == null) {
            int defaultMnuItemResId = R.id.mnuOption1;
            MenuItem defaultItem = navigationView.getMenu().findItem(defaultMnuItemResId);
            if (defaultItem != null) {
                showOption(defaultItem, false);
            }
        }
    }

    private void initViews() {
        drawerLayout = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        imgProfile = ViewCompat.requireViewById(navigationView.getHeaderView(0), R.id.imgProfile);
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        PicassoUtils.loadUrl(imgProfile, "https://picsum.photos/200/200?image=85", R.drawable.background_poly);
        SwitchCompat swDownloadedOnly = (SwitchCompat) navigationView.getMenu().findItem(
                R.id.mnuDownloaded).getActionView();
        swDownloadedOnly.setOnCheckedChangeListener(
                (buttonView, isChecked) -> Toast.makeText(MainActivity.this,
                        isChecked ? getString(R.string.main_activity_downloaded_only) : getString(
                                R.string.main_activity_also_not_downloaded), Toast.LENGTH_SHORT)
                        .show());
        navigationView.setNavigationItemSelectedListener(this);
        if (!readShownPreference()) {
            drawerLayout.openDrawer(GravityCompat.START);
            saveShownPreference();
        }
    }

    private void showOption(MenuItem menuItem, boolean addToBackStack) {
        switch (menuItem.getItemId()) {
            case R.id.mnuOption1:
                if (addToBackStack) {
                    FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),
                            R.id.flContent, new Option1Fragment(),
                            Option1Fragment.class.getSimpleName(),
                            Option1Fragment.class.getSimpleName(),
                            FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                } else {
                    FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                            new Option1Fragment(), Option1Fragment.class.getSimpleName());
                }
                menuItem.setChecked(true);
                break;
            case R.id.mnuOption2:
                FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),
                        R.id.flContent, new Option2Fragment(),
                        Option2Fragment.class.getSimpleName(),
                        Option2Fragment.class.getSimpleName(),
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                menuItem.setChecked(true);
                break;
            case R.id.mnuOption3:
                FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),
                        R.id.flContent, new Option3Fragment(),
                        Option3Fragment.class.getSimpleName(),
                        Option3Fragment.class.getSimpleName(),
                        FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                menuItem.setChecked(true);
                break;
            case R.id.mnuDetail:
                startActivity(new Intent(this, DetailActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(
                item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        showOption(menuItem, true);
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void saveShownPreference() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_NAV_DRAWER_OPENED, true);
        editor.apply();
    }

    private boolean readShownPreference() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(PREF_NAV_DRAWER_OPENED, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onToolbarAvailable(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        if (actionBarDrawerToggle != null) {
            drawerLayout.removeDrawerListener(actionBarDrawerToggle);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.main_activity_navigation_drawer_open,
                R.string.main_activity_navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onFragmentShown(int menuItemResId) {
        MenuItem menuItem = navigationView.getMenu().findItem(menuItemResId);
        if (menuItem != null) {
            menuItem.setChecked(true);
        }
    }
}
