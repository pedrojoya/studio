package es.iessaladillo.pedrojoya.pr120.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import es.iessaladillo.pedrojoya.pr120.R;
import es.iessaladillo.pedrojoya.pr120.fragmentos.AlumnoFragment;

public class AlumnoActivity extends ActionBarActivity {

    private AlumnoFragment frgAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se activa el icono de la aplicación.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Se carga el fragmento de detalle en el FrameLayout de la actividad
        // principal, pasándole como argumento el modo en el que debe funcionar
        // el fragmento y el id del alumno.
        FragmentManager gestorFragmentos = this.getSupportFragmentManager();
        FragmentTransaction transaccion = gestorFragmentos.beginTransaction();
        frgAlumno = new AlumnoFragment();
        Bundle argumentos = new Bundle();
        argumentos.putString(AlumnoFragment.EXTRA_MODO, getIntent().getExtras()
                .getString(AlumnoFragment.EXTRA_MODO));
        argumentos.putLong(AlumnoFragment.EXTRA_ID, getIntent().getExtras()
                .getLong(AlumnoFragment.EXTRA_ID));
        frgAlumno.setArguments(argumentos);
        transaccion.add(android.R.id.content, frgAlumno);
        transaccion.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se infla el menú para la ActionBar.
        getMenuInflater().inflate(R.menu.menu_activity_alumno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo de la opción de menú seleccionada.
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            break;
        case R.id.mnuAlumnoGuardar:
            // Se llama al método correspondiente del fragmento.
            frgAlumno.guardarAlumno();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
