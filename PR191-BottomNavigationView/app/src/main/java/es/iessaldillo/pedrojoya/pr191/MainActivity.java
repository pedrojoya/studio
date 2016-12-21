package es.iessaldillo.pedrojoya.pr191;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FAVORITOS = "TAG_FAVORITOS";
    private static final String TAG_CALENDARIO = "TAG_CALENDARIO";
    private static final String TAG_MUSICA = "TAG_MUSICA";

    private TextView lblOpcion;
    private BottomNavigationView nav;
    private FragmentManager mGestorFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestorFragmentos = getSupportFragmentManager();
        initVistas();
    }

    private void initVistas() {
        lblOpcion = (TextView) findViewById(R.id.lblOpcion);
        nav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuFavoritos:
                        showFavoritos();
                        break;
                    case R.id.mnuCalendario:
                        showCalendario();
                        break;
                    case R.id.mnuMusica:
                        showMusica();
                        break;
                }
                return true;
            }
        });
        // Se simula la pulsación en la primera opción.
        nav.findViewById(R.id.mnuFavoritos).performClick();
    }

    private void showFavoritos() {
        mGestorFragmentos
                .beginTransaction()
                .replace(R.id.flContent, MainFragment.newInstance(getString(R.string.favoritos)),
                        TAG_FAVORITOS )
                .commitNow();
        // Se deshabilita la opción música.
        nav.getMenu().findItem(R.id.mnuMusica).setEnabled(false);
    }

    private void showCalendario() {
        mGestorFragmentos
                .beginTransaction()
                .replace(R.id.flContent, MainFragment.newInstance(getString(R.string.calendario)),
                        TAG_CALENDARIO )
                .commitNow();
        // Se habilita la opción música.
        nav.getMenu().findItem(R.id.mnuMusica).setEnabled(true);
    }

    private void showMusica() {
        mGestorFragmentos
                .beginTransaction()
                .replace(R.id.flContent, MainFragment.newInstance(getString(R.string.musica)),
                        TAG_MUSICA )
                .commitNow();
    }

}
