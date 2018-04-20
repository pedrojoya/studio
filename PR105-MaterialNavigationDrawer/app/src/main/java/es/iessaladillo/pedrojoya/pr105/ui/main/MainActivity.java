package es.iessaladillo.pedrojoya.pr105.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;
import es.iessaladillo.pedrojoya.pr105.ui.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr105.ui.main.option1.Option1Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option2.Option2Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option3.Option3Fragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnToolbarAvailableListener {

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
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }
    }

    private void initViews() {
        drawerLayout = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        imgProfile = ViewCompat.requireViewById(navigationView.getHeaderView(0), R.id.imgProfile);
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        Picasso.with(this).load("http://lorempixel.com/200/200/people/").into(imgProfile);
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

    private void showOption(int itemId, String title, MenuItem menuItem) {
        switch (itemId) {
            case R.id.mnuOption1:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                        new Option1Fragment(), title).commit();
                menuItem.setChecked(true);
                break;
            case R.id.mnuOption2:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                        new Option2Fragment(), title).commit();
                menuItem.setChecked(true);
                break;
            case R.id.mnuOption3:
                getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                        new Option3Fragment(), title).commit();
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
        showOption(menuItem.getItemId(), menuItem.getTitle().toString(), menuItem);
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
                R.string.main_activity_navigation_drawer_open, R.string.main_activity_navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}
