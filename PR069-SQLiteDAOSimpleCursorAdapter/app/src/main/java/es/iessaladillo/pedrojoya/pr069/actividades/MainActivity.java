package es.iessaladillo.pedrojoya.pr069.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr069.R;
import es.iessaladillo.pedrojoya.pr069.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr069.fragmentos.ListaAlumnosFragment;
import es.iessaladillo.pedrojoya.pr069.fragmentos.ListaAlumnosFragment.OnListaAlumnosFragmentListener;
import es.iessaladillo.pedrojoya.pr069.fragmentos.SiNoDialogFragment;
import es.iessaladillo.pedrojoya.pr069.fragmentos.SiNoDialogFragment.SiNoDialogListener;

public class MainActivity extends AppCompatActivity implements
        OnListaAlumnosFragmentListener, SiNoDialogListener {

    private static final String TAG_LISTA_FRAGMENT = "ListaAlumnosFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se carga el layout que incluye de forma estática el fragmento.
        setContentView(R.layout.activity_main);
        // Se carga el fragmento.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContenido, new ListaAlumnosFragment(), TAG_LISTA_FRAGMENT)
                .commit();
    }

    // Muestra la actividad de alumno en "modo agregar".
    @Override
    public void onAgregarAlumno() {
        Intent i = new Intent(this, AlumnoActivity.class);
        // Se establece un extra para indicar que querremos que la actividad
        // de alumno funcione en "modo agregar".
        i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_AGREGAR);
        startActivity(i);
    }

    // Muestra la actividad de alumno en "modo editar".
    @Override
    public void onEditarAlumno(long id) {
        Intent i = new Intent(this, AlumnoActivity.class);
        // Se establece un extra para indicar que querremos que la actividad
        // de alumno funcione en "modo editar".
        i.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_EDITAR);
        // Se pasa el ID del alumno que queremos editar.
        i.putExtra(AlumnoFragment.EXTRA_ID, id);
        startActivity(i);
    }

    // Muestra el fragmento de diálogo de confirmación de eliminación.
    @Override
    public void onConfirmarEliminarAlumnos() {
        SiNoDialogFragment frgDialogo = new SiNoDialogFragment();
        frgDialogo.show(getSupportFragmentManager(), "SiNoDialogFragment");
    }

    // Se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        // Se llama al método del fragmento para eliminar los alumnos
        // seleccionados.
        ListaAlumnosFragment frgListaAlumnos = (ListaAlumnosFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_LISTA_FRAGMENT);
        frgListaAlumnos.eliminarAlumnos();
    }

    // No se confirma la eliminación de los alumnos seleccionados.
    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        // Método requerido por la interfaz SiNoDialogListener.
    }

}
