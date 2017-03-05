package es.iessaladillo.pedrojoya.pr120.actividades;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import es.iessaladillo.pedrojoya.pr120.R;
import es.iessaladillo.pedrojoya.pr120.datos.InstitutoContract;
import es.iessaladillo.pedrojoya.pr120.datos.InstitutoProvider;
import es.iessaladillo.pedrojoya.pr120.fragmentos.AlumnoFragment;
import es.iessaladillo.pedrojoya.pr120.models.Alumno;


public class BusquedaActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int LOADER_BUSQUEDA = 1;

    private String mTermino;
    private SimpleCursorAdapter mAdaptador;

    private ListView mLstResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        // La toolbar actuará como action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initVistas();
        // Se detecta la acción con la que ha sido llamada.
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // El usuario ha enviado un término de búsqueda, que usamos
            // para llevarla a cabo.
            String termino = intent.getStringExtra(SearchManager.QUERY);
            buscar(termino);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // El usuario ha seleccionado una sugerencia.
            // Recibimos la uri correspondiente para acceder a dicho alumno a través del
            // content provider.
            Uri uriAlumno = intent.getData();
            String idAlumno = uriAlumno.getLastPathSegment();
            mostrarAlumno(Long.parseLong(idAlumno));
        }
    }

    private void initVistas() {
        mLstResultados = (ListView) findViewById(R.id.lstResultados);
        String[] from = {InstitutoContract.Alumno.NOMBRE};
        int[] to = {android.R.id.text1};
        mAdaptador = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from,
                to, 0);
        mLstResultados.setAdapter(mAdaptador);
        mLstResultados.setOnItemClickListener(this);
    }

    // Realiza la búsqueda en base al término recibido.
    private void buscar(String termino) {
        mTermino = termino;
        getSupportLoaderManager().initLoader(LOADER_BUSQUEDA, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Se consultan los alumnos cuyo nombre contiene el término introducido.
        String criteria = InstitutoContract.Alumno.NOMBRE + " LIKE '%" + mTermino + "%'";
        return new CursorLoader(this, InstitutoProvider.CONTENT_URI_ALUMNOS,
                InstitutoContract.Alumno.TODOS, criteria, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdaptador.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdaptador.changeCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Cuando se pulsa sobre un elemento de la lista se muestra la actividad
        // con la ficha del alumno.
        Cursor cursor = (Cursor) mLstResultados.getItemAtPosition(position);
        Alumno alumno = Alumno.fromCursor(cursor);
        mostrarAlumno(alumno.getId());
    }

    // Muestra la actividad con la ficha del alumno.
    private void mostrarAlumno(long idAlumno) {
        Intent intentAlumno = new Intent(this, AlumnoActivity.class);
        intentAlumno.putExtra(AlumnoFragment.EXTRA_MODO, AlumnoFragment.MODO_EDITAR);
        intentAlumno.putExtra(AlumnoFragment.EXTRA_ID, idAlumno);
        startActivity(intentAlumno);
        finish();
    }

}
