package es.iessaladillo.pedrojoya.pr246_navigation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setSupportActionBar((Toolbar) ActivityCompat.requireViewById(this, R.id.toolbar));
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        drawerLayout = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
        bottomNavigationView = ActivityCompat.requireViewById(this, R.id.bottomNavigationView);
        // Asociamos el el controlador de navegación con los distintos elementos de navegación.
        // IMPORTANTE: La bottomNavigationView siempre crea una nueva instancia del fragmento de
        // destino. Sin embargo la tecla de Atrás o la de Up hacen pop en la backstrack, navegando
        // al fragmento anterior (la instancia existente).
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Se le pasa la navegación hacia arriba al navegador.
        return NavigationUI.navigateUp(drawerLayout, navController);
    }

}
