package es.iessaladillo.pedrojoya.pr246_navigation;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import androidx.navigation.Navigation;
import es.iessaladillo.pedrojoya.pr246_navigation.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setSupportActionBar((Toolbar) ActivityCompat.requireViewById(this, R.id.toolbar));
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Se le pasa la navegación hacia arriba al navegador.
        return Navigation.findNavController(this, R.id.navHostFragment).navigateUp();
    }

}
