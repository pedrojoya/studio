package es.iessaladillo.pedrojoya.pr064.actividades;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr064.R;
import es.iessaladillo.pedrojoya.pr064.fragmentos.PreferenciasFragment;

public class PreferenciasActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Se muestra el fragmento en la actividad.
        getFragmentManager().beginTransaction()
                .replace(R.id.flContenido, new PreferenciasFragment())
                .commit();
    }
}
