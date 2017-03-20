package pedrojoya.iessaladillo.es.pr201;

import android.os.Bundle;
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
    private static final String STATE_ORDEN = "estadoOrden";

    private RecyclerView lstAlumnos;
    private AlumnosAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private TextView mEmptyView;
    private Parcelable mEstadoLista;
    private int mOrden = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
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
        mAdaptador = new AlumnosAdapter(DB.getInstance().getAlumnos());
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
        }
    }

    // Muestra u oculta la empty view dependiendo de si el adaptador está vacío.
    private void checkAdapterIsEmpty() {
        mEmptyView.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno);
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Snackbar.make(lstAlumnos, getString(R.string.ha_pulsado_sobre, alumno.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
        outState.putInt(STATE_ORDEN, mOrden);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Se obtiene el estado anterior de la lista.
        mEstadoLista = savedInstanceState.getParcelable(STATE_LISTA);
        mOrden = savedInstanceState.getInt(STATE_ORDEN);
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
