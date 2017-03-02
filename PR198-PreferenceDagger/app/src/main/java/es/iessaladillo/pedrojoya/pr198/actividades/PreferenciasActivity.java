package es.iessaladillo.pedrojoya.pr198.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr198.R;
import es.iessaladillo.pedrojoya.pr198.fragmentos.PreferenciasFragment;

public class PreferenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Se muestra el fragmento en la actividad.
        getFragmentManager().beginTransaction()
                .replace(R.id.flContenido, new PreferenciasFragment())
                .commit();
    }
}
