package es.iessaladillo.pedrojoya.pr158;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener,
        AlumnosAdapter.OnItemLongClickListener, RealmChangeListener {

    private static final String STATE_LISTA = "estadoLista";

    private AlumnosAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private Parcelable mEstadoLista;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Se habilita el uso de transiciones entre actividades.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtiene la instancia de Realm y se configura. La actividad actuará
        // como listener cuando se produzcan cambios en ella.
        // a ejecutar.
        mRealm = Realm.getInstance(getApplicationContext());
        mRealm.addChangeListener(this);
        // Se obtienen e inicializan las vistas.
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Configura el FAB.
    private void configFab() {
        FloatingActionButton fabAccion = (FloatingActionButton) findViewById(R.id.fabAccion);
        fabAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se inicia la actividad de detalle para añadir.
                DetalleActivity.start(MainActivity.this);
            }
        });
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        RecyclerView lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(mRealm, getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.setEmptyView(findViewById(R.id.lblNoHayAlumnos));
        lstAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    // Retorna la lista de alumnos ordenados por nombre.
    private RealmResults<Alumno> getAlumnos() {
         return mRealm.where(Alumno.class).findAllSortedAsync("nombre");
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        // Se inicia la actividad de detalle para actualización.
        DetalleActivity.start(this, alumno.getId(), view.findViewById(R.id.imgFoto));
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
    protected void onDestroy() {
        // Se eliminan todos los listener a la base de datos y se cierra.
        mRealm.removeAllChangeListeners();
        mRealm.close();
        super.onDestroy();
    }

    // Cuando se producen cambios en la base de datos.
    @Override
    public void onChange() {
        // Se notifica al adaptador para que los dibuje.
        mAdaptador.notifyDataSetChanged();
    }

}
