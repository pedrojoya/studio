package es.iessaladillo.pedrojoya.pr254;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawerLayout;
    private AppBarLayout appBarLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        appBarLayout = ActivityCompat.requireViewById(this, R.id.appBarLayout);
        navController.addOnDestinationChangedListener(
            (controller, destination, arguments) -> resetToolbarAndBottomNavigationBarPosition());
        drawerLayout = findViewById(R.id.drawerLayout);
        // Title isn't preserved automatically on configuration change with diferent layouts
        navController.addOnDestinationChangedListener(
            (controller, destination, arguments) -> setTitle(destination.getLabel()));
        if (drawerLayout != null) {
            // Navigation drawer mode.
            setupToolbarWithDrawerLayout();
            setupNavigationDrawer();
        } else {
            // BottomNavigationView mode
            setupToolbar();
            setupBottomNavigationView();
        }
    }

    private void resetToolbarAndBottomNavigationBarPosition() {
        CoordinatorLayout.LayoutParams params =
            (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) params.getBehavior();
        if (appBarLayoutBehavior != null) {
            appBarLayoutBehavior.setTopAndBottomOffset(0);
        }
        if (bottomNavigationView != null) {
            bottomNavigationView.setTranslationY(0);
        }
    }

    private void setupToolbarWithDrawerLayout() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.mainDestination, R.id.galleryDestination,
            R.id.slideshowDestination).setDrawerLayout(drawerLayout).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    private void setupNavigationDrawer() {
        NavigationView navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.mainDestination, R.id.galleryDestination, R.id.slideshowDestination).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView = ActivityCompat.requireViewById(this,
            R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
            || super.onOptionsItemSelected(item);
    }

}
