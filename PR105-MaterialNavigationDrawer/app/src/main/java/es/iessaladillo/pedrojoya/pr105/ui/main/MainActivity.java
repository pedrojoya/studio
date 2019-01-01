package es.iessaladillo.pedrojoya.pr105.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;
import es.iessaladillo.pedrojoya.pr105.ui.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr105.ui.main.option1.Option1Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option2.Option2Fragment;
import es.iessaladillo.pedrojoya.pr105.ui.main.option3.Option3Fragment;
import es.iessaladillo.pedrojoya.pr105.utils.PicassoUtils;


public class MainActivity extends AppCompatActivity implements OnToolbarAvailableListener {

    private static final String PREFERENCES_FILE = "prefs";
    private static final String PREF_NAV_DRAWER_OPENED = "navdrawerOpened";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CircleImageView imgProfile;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
            MainActivityViewModel.class);
        setupViews();
        observeCurrentOption();
        if (savedInstanceState == null) {
            navigateToStartOption();
        }
    }

    private void setupViews() {
        drawerLayout = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        imgProfile = ViewCompat.requireViewById(navigationView.getHeaderView(0), R.id.imgProfile);

        setupNavigationDrawer();
        addOnBackPressedCallback(this::closeDrawerIfOpen);
    }

    private boolean closeDrawerIfOpen() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private void setupNavigationDrawer() {
        PicassoUtils.loadUrl(imgProfile, "https://picsum.photos/200/200?image=85",
            R.drawable.background_poly);
        SwitchCompat swDownloadedOnly = (SwitchCompat) navigationView.getMenu().findItem(
            R.id.mnuDownloaded).getActionView();
        swDownloadedOnly.setOnCheckedChangeListener(
            (buttonView, isChecked) -> Toast.makeText(MainActivity.this,
                isChecked ? getString(R.string.main_activity_downloaded_only) : getString(
                    R.string.main_activity_also_not_downloaded), Toast.LENGTH_SHORT).show());
        navigationView.setNavigationItemSelectedListener(this::onNavItemSelected);
        if (!readShownPreference()) {
            drawerLayout.openDrawer(GravityCompat.START);
            saveShownPreference();
        }
    }

    private void navigateToStartOption() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
            Option1Fragment.newInstance(), Option1Fragment.class.getSimpleName()).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private void navigateToOption(MenuItem menuItem) {
        Integer currentOption = viewModel.getCurrentOption().getValue();
        if (currentOption == null || currentOption != menuItem.getItemId()) {
            switch (menuItem.getItemId()) {
                case R.id.mnuOption1:
                    replaceFragment(Option1Fragment.newInstance(),
                        Option1Fragment.class.getSimpleName());
                    menuItem.setChecked(true);
                    break;
                case R.id.mnuOption2:
                    replaceFragment(Option2Fragment.newInstance(),
                        Option2Fragment.class.getSimpleName());
                    menuItem.setChecked(true);
                    break;
                case R.id.mnuOption3:
                    replaceFragment(Option3Fragment.newInstance(),
                        Option3Fragment.class.getSimpleName());
                    menuItem.setChecked(true);
                    break;
                case R.id.mnuDetail:
                    DetailActivity.start(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(
            item);
    }

    @SuppressWarnings("SameReturnValue")
    private boolean onNavItemSelected(@NonNull MenuItem menuItem) {
        navigateToOption(menuItem);
        drawerLayout.closeDrawers();
        return true;
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

    @Override
    public void onToolbarAvailable(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
        if (actionBarDrawerToggle != null) {
            drawerLayout.removeDrawerListener(actionBarDrawerToggle);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
            R.string.main_activity_navigation_drawer_open,
            R.string.main_activity_navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void observeCurrentOption() {
        viewModel.getCurrentOption().observe(this, option -> {
            MenuItem menuItem = navigationView.getMenu().findItem(option);
            if (menuItem != null) {
                menuItem.setChecked(true);
            }
        });
    }

}
