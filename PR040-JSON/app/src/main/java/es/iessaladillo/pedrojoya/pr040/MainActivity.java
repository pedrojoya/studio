package es.iessaladillo.pedrojoya.pr040;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<ArrayList<Alumno>> {

    private static final int LOADER_ID = 1;

    private SwipeRefreshLayout swlPanel;
    private AlumnosAdapter mAdaptador;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se inicia el cargador. La actividad actuará como listener.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        ListView lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setEmptyView(findViewById(R.id.lblEmpty));
            // El adaptador inicialmente recibe una lista vacía.
            mAdaptador = new AlumnosAdapter(this, new ArrayList<Alumno>());
            lstAlumnos.setAdapter(mAdaptador);
        }
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
        if (swlPanel != null) {
            swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swlPanel.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
        // Se reinicia el cargador.
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    // Cuando se debe crear el loader. Retorna el cargador.
    @Override
    public Loader<ArrayList<Alumno>> onCreateLoader(int id, Bundle args) {
        Log.d(getString(R.string.app_name), "onCreateLoader");
        // Se retorna el loader.
        return new AlumnosLoader(this);
    }

    // Cuando el loader entrega datos.
    @Override
    public void onLoadFinished(Loader<ArrayList<Alumno>> loader,
                               ArrayList<Alumno> data) {
        Log.d(getString(R.string.app_name), "onLoaderFinished");
        swlPanel.setRefreshing(false);
        // Se actualizan los datos del adaptador.
        mAdaptador.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Alumno>> loader) {
        Log.d(getString(R.string.app_name), "onLoaderReset");
        // Se anulan los datos del adaptador.
        mAdaptador.setData(null);
    }

}
