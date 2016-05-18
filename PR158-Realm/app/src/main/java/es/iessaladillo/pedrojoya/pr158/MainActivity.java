package es.iessaladillo.pedrojoya.pr158;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener,
        AlumnosAdapter.OnItemLongClickListener, RealmChangeListener<RealmResults<Alumno>> {

    private static final int RC_DETALLE = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private AlumnosAdapter mAdaptador;
    private Realm mRealm;
    private RecyclerView.AdapterDataObserver mObservador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Se habilita el uso de transiciones entre actividades.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Se obtiene la instancia de Realm y se configura. La actividad actuará
        // como listener cuando se produzcan cambios en ella.
        // a ejecutar.
        mRealm = Realm.getDefaultInstance();
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        crearAsignaturas();
    }

    private void crearAsignaturas() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId("PMDMO");
        asignatura.setNombre("Android");
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(asignatura);
        mRealm.commitTransaction();
        Asignatura asignatura2 = new Asignatura();
        asignatura.setId("PSPRO");
        asignatura.setNombre("Multihilo");
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(asignatura);
        mRealm.commitTransaction();
        Asignatura asignatura3 = new Asignatura();
        asignatura.setId("HLC");
        asignatura.setNombre("Horas de libre configuración");
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(asignatura);
        mRealm.commitTransaction();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fabAccion)
    public void agregar() {
        // Se inicia la actividad de detalle para añadir.
        DetalleActivity.startForResult(MainActivity.this, RC_DETALLE);
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        mAdaptador = new AlumnosAdapter(mRealm, getAlumnos());
        mAdaptador.setOnItemClickListener(MainActivity.this);
        mAdaptador.setOnItemLongClickListener(MainActivity.this);
        lstAlumnos.setHasFixedSize(true);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(new LinearLayoutManager(MainActivity.this,
                LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        checkAdapterIsEmpty();
    }

    private void checkAdapterIsEmpty() {
        lblNoHayAlumnos.setVisibility(
                mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    // Retorna la lista de alumnos ordenados por nombre.
    private RealmResults<Alumno> getAlumnos() {
        RealmResults<Alumno> datos = mRealm.where(Alumno.class).findAllSorted("nombre");
        datos.addChangeListener(this);
        return datos;
    }


    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        // Se inicia la actividad de detalle para actualización.
        DetalleActivity.startForResult(this, RC_DETALLE, alumno.getId(),
                view.findViewById(R.id.imgFoto));
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);
    }

    @Override
    protected void onDestroy() {
        // Se eliminan todos los listener a la base de datos y se cierra.
        mRealm.removeAllChangeListeners();
        mRealm.close();
        super.onDestroy();
    }

    @Override
    public void onChange(RealmResults<Alumno> element) {
        // Se notifica al adaptador para que los dibuje.
        Log.d("Mia", "Actualización");
        mAdaptador.notifyDataSetChanged();
        checkAdapterIsEmpty();
    }
}
