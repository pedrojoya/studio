package es.iessaladillo.pedrojoya.pr120.actividades;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr120.R;
import es.iessaladillo.pedrojoya.pr120.datos.InstitutoContract;
import es.iessaladillo.pedrojoya.pr120.datos.InstitutoProvider;
import es.iessaladillo.pedrojoya.pr120.models.Alumno;


public class BusquedaActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int LOADER_BUSQUEDA = 1;
    private String mTermino;

    private ListView mLstResultados;
    private SimpleCursorAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        initVistas();
        // Se obtiene el intent y se verifica la acción.
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Se obtiene el término.
            String termino = intent.getStringExtra(SearchManager.QUERY);
            // Se realiza la búsqueda.
            buscar(termino);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // El usuario ha seleccionado una sugerencia.
            Uri uriAlumno = intent.getData();
            String id = uriAlumno.getLastPathSegment();
            Toast.makeText(this, uriAlumno.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initVistas() {
        mLstResultados = (ListView) findViewById(R.id.lstResultados);
        String[] from = {InstitutoContract.Alumno.NOMBRE};
        int[] to = {android.R.id.text1};
        mAdaptador = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, 0);
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
        String criteria = InstitutoContract.Alumno.NOMBRE + " LIKE '%" + mTermino + "%'";
        return new CursorLoader(this, InstitutoProvider.CONTENT_URI_ALUMNOS, InstitutoContract.Alumno.TODOS, criteria, null, null);
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
        Cursor cursor = (Cursor) mLstResultados.getItemAtPosition(position);
        Alumno alumno = Alumno.fromCursor(cursor);
        Toast.makeText(this, alumno.getNombre() + " - " + alumno.getCurso(), Toast.LENGTH_SHORT).show();
    }
}
