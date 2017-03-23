package es.iessaladillo.pedrojoya.pr158;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.BoxStore;


@SuppressWarnings({"WeakerAccess", "unused"})
public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener,
        AlumnosAdapter.OnItemLongClickListener {

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
    private BoxStore mBoxStore;
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
        mBoxStore = ((App) getApplication()).getBoxStore();
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
        //crearAsignaturas();
    }

//    private void crearAsignaturas() {
//        Asignatura asignatura = new Asignatura();
//        asignatura.setId("PMDMO");
//        asignatura.setNombre("Android");
//        mBoxStore.beginTransaction();
//        mBoxStore.copyToRealmOrUpdate(asignatura);
//        mBoxStore.commitTransaction();
//        Asignatura asignatura2 = new Asignatura();
//        asignatura2.setId("PSPRO");
//        asignatura2.setNombre("Multihilo");
//        mBoxStore.beginTransaction();
//        mBoxStore.copyToRealmOrUpdate(asignatura2);
//        mBoxStore.commitTransaction();
//        Asignatura asignatura3 = new Asignatura();
//        asignatura3.setId("HLC");
//        asignatura3.setNombre("Horas de libre configuración");
//        mBoxStore.beginTransaction();
//        mBoxStore.copyToRealmOrUpdate(asignatura3);
//        mBoxStore.commitTransaction();
//    }

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
        mAdaptador = new AlumnosAdapter(getAlumnos());
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
    private List<Alumno> getAlumnos() {
        return mBoxStore.boxFor(Alumno.class).getAll();
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

}
