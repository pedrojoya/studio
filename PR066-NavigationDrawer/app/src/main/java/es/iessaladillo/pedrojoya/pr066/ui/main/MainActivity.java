package es.iessaladillo.pedrojoya.pr066.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr066.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

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
    }

    private void setupNavigationDrawer() {
        drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, MainFragment
                .newInstance(title)).commit();
    }

}
