package es.iessaladillo.pedrojoya.pr195;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LIST_DATA = "STATE_LIST_DATA";
    private static final String STATE_LIST_STATE = "STATE_LIST_STATE";

    private SwipeRefreshLayout swlPanel;
    private AlumnosAdapter mAdaptador;
    private CompositeDisposable mCompositeDisposable;
    private ListView lstAlumnos;
    private Parcelable mEstadoLista;

    // Al crearse la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(es.iessaladillo.pedrojoya.pr195.R.layout.activity_main);
        initVistas();
        mCompositeDisposable = new CompositeDisposable();
        if (savedInstanceState == null) {
            swlPanel.post(new Runnable() {
                @Override
                public void run() {
                    swlPanel.setRefreshing(true);
                    loadAlumnos();
                }
            });
        } else {
            ArrayList<Alumno> alumnos = savedInstanceState.getParcelableArrayList(STATE_LIST_DATA);
            if (alumnos != null) {
                mAdaptador.addAll(alumnos);
            }
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        lstAlumnos = (ListView) findViewById(es.iessaladillo.pedrojoya.pr195.R.id.lstAlumnos);
        if (lstAlumnos != null) {
            lstAlumnos.setEmptyView(findViewById(es.iessaladillo.pedrojoya.pr195.R.id.lblEmpty));
            // El adaptador inicialmente recibe una lista vacía.
            mAdaptador = new AlumnosAdapter(this, new ArrayList<Alumno>());
            lstAlumnos.setAdapter(mAdaptador);
        }
        swlPanel = (SwipeRefreshLayout) findViewById(es.iessaladillo.pedrojoya.pr195.R.id.swlPanel);
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
        loadAlumnos();
    }

    private void loadAlumnos() {
        Api.ApiInterface api = Api.getApiInterface(this);
        Disposable disposable = api.getAlumnos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Alumno>>() {
                    @Override
                    public void accept(List<Alumno> alumnos) throws Exception {
                        mAdaptador.addAll(alumnos);
                        swlPanel.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Error en el Observable",
                                Toast.LENGTH_SHORT).show();
                        swlPanel.setRefreshing(false);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_LIST_DATA, mAdaptador.getData());
        outState.putParcelable(STATE_LIST_STATE, lstAlumnos.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mEstadoLista = savedInstanceState.getParcelable(STATE_LIST_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Se retaura el estado de la lista.
        if (mEstadoLista != null) {
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

}
