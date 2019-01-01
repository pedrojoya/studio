package es.iessaladillo.pedrojoya.pr066.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr066.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        // if just lunched, select default menu item in drawer.
        if (savedInstanceState == null) {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }

    }

    private void setupViews() {
        setupToolbar();
        setupNavigationDrawer();
    }

    private void setupToolbar() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupNavigationDrawer() {
        drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.main_open_navigation_drawer, R.string.main_close_navigation_drawer);
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
                navigateToOption(item.getTitle().toString());
                item.setChecked(true);
                break;
            case R.id.mnuOption5:
                navigateToOption5Activity();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToOption5Activity() {
        Toast.makeText(this, getString(R.string.main_navigateToOption5), Toast.LENGTH_SHORT).show();
    }

    private void navigateToOption(String title) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, MainFragment.newInstance(title), MainFragment.class.getSimpleName())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

}
