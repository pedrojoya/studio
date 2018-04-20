package es.iessaladillo.pedrojoya.pr120.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import es.iessaladillo.pedrojoya.pr120.R;
import es.iessaladillo.pedrojoya.pr120.fragmentos.AlumnoFragment;

public class AlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        // La toolbar actuará como action bar.
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Se carga el fragmento de detalle en el FrameLayout de la actividad
        // principal, pasándole como argumento el modo en el que debe funcionar
        // el fragmento y el id del alumno.
        AlumnoFragment frgAlumno = AlumnoFragment.newInstance(
                getIntent().getExtras().getString(AlumnoFragment.EXTRA_MODO),
                getIntent().getExtras().getLong(AlumnoFragment.EXTRA_ID));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContenido, frgAlumno)
                .commit();
    }

}
