package es.iessaladillo.pedrojoya.pr257.another;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr257.R;

public class AnotherActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        navController = Navigation.findNavController(this, R.id.anotherNavHostFragment);
        setupAppBar();
    }

    private void setupAppBar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        // No hay ningún fragmento de primer nivel (para que el fragmento inicial muestra
        // el icono de navigateUp.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder()
            // Para que vuelva a la actividad que la llamó.
            .setFallbackOnNavigateUpListener(() -> {
                onBackPressed();
                return true;
            })
            .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

}
