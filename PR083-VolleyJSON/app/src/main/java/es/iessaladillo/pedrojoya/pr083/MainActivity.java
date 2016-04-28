package es.iessaladillo.pedrojoya.pr083;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<ArrayList<Alumno>>{

    private static final int LOADER_ID = 1;

    private SwipeRefreshLayout swlPanel;
    private AlumnosAdapter mAdaptador;
    private TextView lblEmpty;
    private RecyclerView.AdapterDataObserver mObservador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se inicia el cargador. La actividad actuará como ArrayListener.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        configSwipeRefresh();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        lblEmpty = (TextView) findViewById(R.id.lblEmpty);
        // Se agrega un observador al adaptador para cuando se agreguen o eliminen datos en el
        // adaptador se muestre u oculte la empty view en consecuencia.
        mAdaptador = new AlumnosAdapter(null);
        mObservador = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkAdapterIsEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkAdapterIsEmpty();
            }
        };
        mAdaptador.registerAdapterDataObserver(mObservador);
        RecyclerView lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setHasFixedSize(true);
            lstAlumnos.setAdapter(mAdaptador);
            lstAlumnos.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));
            lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        }
    }

    // Muestra u oculta la empty view.
    private void checkAdapterIsEmpty() {
        lblEmpty.setVisibility(mAdaptador.isEmpty()?View.VISIBLE:View.INVISIBLE);
    }

    // Configura el SwipeRefreshLayout.
    private void configSwipeRefresh() {
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
        mAdaptador.swapData(data);
        checkAdapterIsEmpty();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Alumno>> loader) {
        Log.d(getString(R.string.app_name), "onLoaderReset");
        // Se anulan los datos del adaptador.
        mAdaptador.swapData(null);
    }

    @Override
    protected void onDestroy() {
        mAdaptador.unregisterAdapterDataObserver(mObservador);
        super.onDestroy();
    }

}