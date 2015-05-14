package es.iessaladillo.pedrojoya.pr118.actividades;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr118.R;
import es.iessaladillo.pedrojoya.pr118.datos.BusquedaProvider;
import es.iessaladillo.pedrojoya.pr118.datos.InstitutoContract;
import es.iessaladillo.pedrojoya.pr118.datos.InstitutoProvider;
import es.iessaladillo.pedrojoya.pr118.models.Alumno;


public class BusquedaActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final int LOADER_BUSQUEDA = 1;
    private String mTermino;

    private ListView mLstResultados;
    private SimpleCursorAdapter mAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        initVistas();
        // Se obtiene el intent y se verifica la acción.
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Se obtiene el término.
            String termino = intent.getStringExtra(SearchManager.QUERY);
            // Se guarda el término en el content provider de búsqueda.
            guardarTermino(termino);
            // Se realiza la búsqueda.
            buscar(termino);
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

    // Guarda el término recibido en el content provider de búsqueda.
    private void guardarTermino(String termino) {
        // Se obtiene el objeto de sugerencias.
        SearchRecentSuggestions sugerencias = new SearchRecentSuggestions(this,
                BusquedaProvider.AUTHORITY, BusquedaProvider.MODE);
        // Se almacena el término en las sugerencias.
        sugerencias.saveRecentQuery(termino, null);
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