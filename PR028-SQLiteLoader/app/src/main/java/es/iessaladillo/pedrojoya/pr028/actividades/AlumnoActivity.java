package es.iessaladillo.pedrojoya.pr028.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr028.R;
import es.iessaladillo.pedrojoya.pr028.fragmentos.AlumnoFragment;

public class AlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
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