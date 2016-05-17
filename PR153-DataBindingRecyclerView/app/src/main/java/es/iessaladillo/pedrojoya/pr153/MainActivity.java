package es.iessaladillo.pedrojoya.pr153;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import es.iessaladillo.pedrojoya.pr153.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr153.dbutils.RecyclerBindingAdapter;


public class MainActivity extends AppCompatActivity implements RecyclerBindingAdapter.OnItemClickListener<Alumno>, RecyclerBindingAdapter.OnItemLongClickListener<Alumno> {

    private static final String STATE_LISTA = "estadoLista";

    private ActivityMainBinding binding;

    private LinearLayoutManager mLayoutManager;
    private Parcelable mEstadoLista;
    private RecyclerBindingAdapter<Alumno> mAdaptador;
    private RecyclerView.AdapterDataObserver mObservador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        configFab();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el FAB.
    private void configFab() {
        binding.fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarAlumno(DB.getNextAlumno());
            }
        });
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        binding.lstAlumnos.setHasFixedSize(true);
        //mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador = new RecyclerBindingAdapter<>(DB.getAlumnos(), es.iessaladillo.pedrojoya.pr153.BR.alumno);
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        //mAdaptador.setEmptyView(binding.lblNoHayAlumnos);
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
        binding.lstAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        binding.lstAlumnos.setLayoutManager(mLayoutManager);
        binding.lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        checkAdapterIsEmpty();
    }

    private void checkAdapterIsEmpty() {
        binding.lblNoHayAlumnos.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno);
        binding.lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se salva el estado del RecyclerView.
        mEstadoLista = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA, mEstadoLista);
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

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno model, int position) {
        Snackbar.make(binding.lstAlumnos, getString(R.string.ha_pulsado_sobre, model.getNombre()),
                Snackbar.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno model, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }

    @Override
    protected void onDestroy() {
        // Se quita el registro el observador.
        mAdaptador.unregisterAdapterDataObserver(mObservador);
        super.onDestroy();
    }

}
