package es.iessaladillo.pedrojoya.pr246_navigation.ui.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr246_navigation.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private int[] firstLevelDestinationIds = new int[]{R.id.option1Fragment, R.id
            .option2Fragment, R.id.option3Fragment};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initViews();
    }

    private void initViews() {
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setupNavigationDrawer();
        setupBottomNavigationBar();
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
//            navController.addOnNavigatedListener(
//                    new FirstLevelActionBarOnNavigatedListener(this, drawerLayout,
//                            firstLevelDestinationIds));
        }
    }

    private void setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            navigationView = findViewById(R.id.navigationView);
            if (navigationView != null) {
                // Connect NavigationView with navigation controller.
                NavigationUI.setupWithNavController(navigationView, navController);
                //setupWithNavController(navigationView, navController);
            }
        }
    }

    private void setupBottomNavigationBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            // Connect NavigationView with navigation controller.
            // IMPORTANTE: La bottomNavigationView siempre crea una nueva instancia del fragmento de
            // destino. Sin embargo la tecla de Atrás o la de Up hacen pop en la backstrack, navegando
            // al fragmento anterior (la instancia existente).
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            // setupWithNavController(bottomNavigationView, navController);
        }
    }

    /* Tenemos que implementar estos métodos porque con el proporcionado por NavigationUI usar
    unas animaciones por defecto cuyo afecto es horrible. El código es copiado de
    NavigationUI evitando las animaciones.
     */

    public void setupWithNavController(@NonNull final NavigationView navigationView,
                                       @NonNull final NavController navController) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        boolean handled = onNavDestinationSelected(item, navController, true);
                        if (handled) {
                            ViewParent parent = navigationView.getParent();
                            if (parent instanceof DrawerLayout) {
                                ((DrawerLayout) parent).closeDrawer(navigationView);
                            }
                        }
                        return handled;
                    }
                });
        navController.addOnNavigatedListener(new NavController.OnNavigatedListener() {
            @Override
            public void onNavigated(@NonNull NavController controller,
                                    @NonNull NavDestination destination) {
                int destinationId = destination.getId();
                Menu menu = navigationView.getMenu();
                for (int h = 0, size = menu.size(); h < size; h++) {
                    MenuItem item = menu.getItem(h);
                    item.setChecked(item.getItemId() == destinationId);
                }
            }
        });
    }

    public void setupWithNavController(
            @NonNull final BottomNavigationView bottomNavigationView,
            @NonNull final NavController navController) {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return onNavDestinationSelected(item, navController, true);
                    }
                });
        navController.addOnNavigatedListener(new NavController.OnNavigatedListener() {
            @Override
            public void onNavigated(@NonNull NavController controller,
                    @NonNull NavDestination destination) {
                int destinationId = destination.getId();
                Menu menu = bottomNavigationView.getMenu();
                for (int h = 0, size = menu.size(); h < size; h++) {
                    MenuItem item = menu.getItem(h);
                    if (item.getItemId() == destinationId) {
                        item.setChecked(true);
                    }
                }
            }
        });
    }

    private boolean onNavDestinationSelected(@NonNull MenuItem item,
            @NonNull NavController navController, boolean popUp) {
        NavOptions.Builder builder = new NavOptions.Builder()
                .setLaunchSingleTop(true);
        // Evitamos hacer las animaciones.
/*
                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim);
*/
        if (popUp) {
            builder.setPopUpTo(navController.getGraph().getStartDestination(), false);
        }
        NavOptions options = builder.build();
        try {
            //TODO provide proper API instead of using Exceptions as Control-Flow.
            navController.navigate(item.getItemId(), null, options);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // return firstLevelNavigateUp(drawerLayout, navController, firstLevelDestinationIds);

        // Se le pasa la navegación hacia arriba al navegador (si el drawerLayout es null
        // no hará nada con el navigation drawer.
        return NavigationUI.navigateUp(drawerLayout, navController);
    }

    private boolean firstLevelNavigateUp(DrawerLayout drawerLayout, NavController navController,
            @NonNull int[] firstLevelDestinationIds) {
        int currentDestinationId = navController.getCurrentDestination().getId();
        if (drawerLayout != null && contains(firstLevelDestinationIds, currentDestinationId)) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            return navController.navigateUp();
        }
    }

    private boolean contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return true;
        }
        return false;
    }

}
