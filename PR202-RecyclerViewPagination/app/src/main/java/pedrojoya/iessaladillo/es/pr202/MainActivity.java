package pedrojoya.iessaladillo.es.pr202;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity implements AlumnosAdapter
        .OnItemClickListener, AlumnosAdapter.OnItemLongClickListener {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_DATA = "estadoData";
    private static final String STATE_CURRENT_PAGE = "estadoCurrentPage";

    private RecyclerView lstAlumnos;
    private AlumnosAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private TextView mEmptyView;
    private ArrayList<Alumno> mData = new ArrayList<>();;
    private Parcelable mEstadoLista;
    private int mOrden = -1;
    private boolean mIsLoading = false;
    private int mCurrentPage;
    private MenuItem mnuOrdenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mData = savedInstanceState.getParcelableArrayList(STATE_DATA);
            mCurrentPage = savedInstanceState.getInt(STATE_CURRENT_PAGE);
        }
        initVistas();
        if (savedInstanceState == null) {
            loadFirstPage();
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        mEmptyView = (TextView) findViewById(R.id.lblNoHayAlumnos);
        configToolbar();
        configRecyclerView();
        configFab();
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
        if (fabAccion != null) {
            fabAccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarAlumno(DB.getInstance().getNextAlumno());
                }
            });
        }
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        mAdaptador = new AlumnosAdapter(mData);
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
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setHasFixedSize(true);
            lstAlumnos.setAdapter(mAdaptador);
            checkAdapterIsEmpty();
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lstAlumnos.setLayoutManager(mLayoutManager);
            lstAlumnos.setItemAnimator(new DefaultItemAnimator());
            lstAlumnos.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    mIsLoading = true;
                    mAdaptador.addLoadingFooter();
                    // Se deshabilita el menú de ordenar.
                    if (mnuOrdenar != null) {
                        mnuOrdenar.setEnabled(false);
                    }
                    // Se obtienen los nuevos datos.
                    loadNextPage();
                }

                @Override
                public boolean isLoading() {
                    return mIsLoading;
                }
            });
        }
    }

    private void loadFirstPage() {
        mAdaptador.addAll(DB.getInstance().getPage(mCurrentPage));
    }

    private void loadNextPage() {
        // Se simula que se tarda un tiempo.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Se obtienen los nuevos datos.
                ArrayList<Alumno> nuevos = DB.getInstance().getPage(mCurrentPage + 1);
                // Se elimina el elemento de loading del adaptador.
                mAdaptador.removeLoadingFooter();
                // Se indica que ya no se está cargando.
                mIsLoading = false;
                // Se añaden los nuevos datos al adaptador.
                mAdaptador.addAll(nuevos);
                mCurrentPage++;
                // Se habilita el menú de ordenar.
                if (mnuOrdenar != null) {
                    mnuOrdenar.setEnabled(true);
                }

            }
        }, 3000);
    }

    // Muestra u oculta la empty view dependiendo de si el adaptador está vacío.
    private void checkAdapterIsEmpty() {
        mEmptyView.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(pedrojoya.iessaladillo.es.pr202.Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno);
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, pedrojoya.iessaladillo.es.pr202.Alumno alumno,
            int position) {
        Snackbar.make(lstAlumnos, getString(R.string.ha_pulsado_sobre, alumno.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, pedrojoya.iessaladillo.es.pr202.Alumno alumno,
            int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        if (mIsLoading) {
            // Se elimina el elemento de loading del adaptador.
            mAdaptador.removeLoadingFooter();
            // Se indica que ya no se está cargando.
            mIsLoading = false;
        }
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putParcelableArrayList(STATE_DATA, mAdaptador.getData());
        outState.putInt(STATE_CURRENT_PAGE, mCurrentPage);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            mLayoutManager.onRestoreInstanceState(mEstadoLista);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        mnuOrdenar = menu.findItem(R.id.mnuOrdenar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuOrdenar) {
            cambiarOrden();

        }
        return super.onOptionsItemSelected(item);
    }

    private void cambiarOrden() {
        ArrayList<Alumno> actual = mAdaptador.getData();
        ArrayList<Alumno> nueva = new ArrayList<>(actual);
        //Collections.reverse(nueva);
        // Se cambia el orden.
        Collections.sort(actual, new Comparator<Alumno>() {
            @Override
            public int compare(Alumno alumno1, Alumno alumno2) {
                return mOrden * alumno1.getNombre().compareTo(alumno2.getNombre());
            }
        });
        mOrden = -mOrden;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new AlumnosDiffUtilCallback(actual, nueva));
        diffResult.dispatchUpdatesTo(mAdaptador);
    }

}
