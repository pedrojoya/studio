package pedrojoya.iessaladillo.es.pr176;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener,
        AlumnosAdapter.OnItemLongClickListener, LoaderManager.LoaderCallbacks<ArrayList<Alumno>>,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LISTA = "estadoLista";
    private static final int LOADER_ID = 1;
    private static final int RC_AGREGAR = 1;

    private RecyclerView lstAlumnos;
    private AlumnosAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private TextView mEmptyView;
    private SwipeRefreshLayout swlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
        // Se inicia el cargador. La actividad actuará como ArrayListener.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mEmptyView = (TextView) findViewById(R.id.lblNoHayAlumnos);
        configToolbar();
        configRecyclerView();
        configFab();
        configPanel();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el FAB.
    private void configFab() {
        FloatingActionButton fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //agregarAlumno(DB.getNextAlumno());
                AlumnoActivity.startForResult(MainActivity.this, RC_AGREGAR);
            }
        });
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        TextView lblNoHayAlumnos = (TextView) findViewById(R.id.lblNoHayAlumnos);
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter();
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
        });
        lstAlumnos.setAdapter(mAdaptador);
        checkAdapterIsEmpty();
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    // Configura el SwipeRefreshLayout.
    private void configPanel() {
        swlPanel = (SwipeRefreshLayout) findViewById(R.id.swlPanel);
        swlPanel.setOnRefreshListener(this);

        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    // Cuando el usuario hace swipe to refresh.
    @Override
    public void onRefresh() {
        refrescar();
    }

    private void refrescar() {
        // Se activa la animación.
        swlPanel.setRefreshing(true);
        // Se reinicia el loader.
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    // Muestra u oculta la empty view dependiendo de si el adaptador está vacío.
    private void checkAdapterIsEmpty() {
        mEmptyView.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Agrega un alumno a la ArrayLista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno);
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    // Cuando se hace click sobre un elemento de la ArrayLista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Snackbar.make(lstAlumnos, getString(R.string.ha_pulsado_sobre, alumno.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la ArrayLista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_AGREGAR && resultCode == RESULT_OK) {
            Log.d(getString(R.string.app_name), "onActivityResult: ");
            // Se reinicia el cargador.
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
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
    public void onLoadFinished(Loader<ArrayList<Alumno>> loader, ArrayList<Alumno> data) {
        Log.d(getString(R.string.app_name), "onLoaderFinished");
        // Se actualizan los datos del adaptador.
        mAdaptador.setData(data);
        // Se comprueba si hay que mostrar la emptyview.
        checkAdapterIsEmpty();
        // Se cancela la animación del panel.
        swlPanel.post(new Runnable() {
            @Override
            public void run() {
                swlPanel.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Alumno>> loader) {
        Log.d(getString(R.string.app_name), "onLoaderReset");
        // Se anulan los datos del adaptador.
        mAdaptador.setData(null);
    }

}
