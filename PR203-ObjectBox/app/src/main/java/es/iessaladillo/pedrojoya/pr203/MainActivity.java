package es.iessaladillo.pedrojoya.pr203;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscription;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
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
    private Box<Alumno> mAlumnoBox;
    private Query<Alumno> mAlumnosQuery;
    private DataSubscription mSubscripcion;
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
        initBD();
        initVistas();
        getAlumnos();
    }

    private void initBD() {
        mBoxStore = ((App) getApplication()).getBoxStore();
        mAlumnoBox = mBoxStore.boxFor(Alumno.class);
        mAlumnosQuery = mAlumnoBox.query().order(Alumno_.nombre).build();
    }

    private void initVistas() {
        configToolbar();
        configRecyclerView();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fabAccion)
    public void agregar() {
        DetalleActivity.start(this);
    }

    private void configRecyclerView() {
        mAdaptador = new AlumnosAdapter(new ArrayList<Alumno>());
        mAdaptador.setOnItemClickListener(MainActivity.this);
        mAdaptador.setOnItemLongClickListener(MainActivity.this);
        lstAlumnos.setHasFixedSize(true);
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

    private void getAlumnos() {
        // Se ejecuta la consulta en un hilo secundario y se crea un observador que actualiza el
        // adpatador cuando hay cambios.
        mSubscripcion = mAlumnosQuery.subscribe().on(AndroidScheduler.mainThread()).observer(
                new DataObserver<List<Alumno>>() {
                    @Override
                    public void onData(List<Alumno> alumnos) {
                        mAdaptador.setData(alumnos);
                        checkAdapterIsEmpty();
                        // QUITAR SETDATA Y USAR DIFFUTIL.
                    }
                });
    }

    @Override
    protected void onDestroy() {
        // Se cancela la subscripción.
        mSubscripcion.cancel();
        // Se quita el registro el observador.
        mAdaptador.unregisterAdapterDataObserver(mObservador);
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        // Se inicia la actividad de detalle para actualización.
        DetalleActivity.start(this, alumno.getId(),
                view.findViewById(R.id.imgFoto));
    }

    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        Box<AsignaturasAlumnos> asignaturasAlumnoBox = mBoxStore.boxFor(AsignaturasAlumnos.class);
        Query<AsignaturasAlumnos> asignaturasAlumnoQuery = asignaturasAlumnoBox.query().equal(
                AsignaturasAlumnos_.alumnoId, alumno.getId()).build();
        asignaturasAlumnoQuery.remove();
        mAlumnoBox.remove(alumno);
    }

}
