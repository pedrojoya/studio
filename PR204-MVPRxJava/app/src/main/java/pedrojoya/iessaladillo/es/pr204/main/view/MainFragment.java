package pedrojoya.iessaladillo.es.pr204.main.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pedrojoya.iessaladillo.es.pr204.R;
import pedrojoya.iessaladillo.es.pr204.base.MessageManager;
import pedrojoya.iessaladillo.es.pr204.component.ToastMessageManager;
import pedrojoya.iessaladillo.es.pr204.main.presenter.MainPresenter;
import pedrojoya.iessaladillo.es.pr204.main.usecases.AlumnosUseCaseImpl;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

public class MainFragment extends Fragment implements MainAdapter.OnItemClickListener, MainView {

    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView emptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private MainPresenter mPresenter;
    private MainAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private Unbinder mUnbinder;
    private RecyclerView.AdapterDataObserver mObservador;
    private MessageManager mMessageManager;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new MainPresenter(this, new AlumnosUseCaseImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            mUnbinder = ButterKnife.bind(this, getView());
            initVistas(getView());
            mMessageManager = new ToastMessageManager();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onViewAttach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onViewDetach();
    }

    private void initVistas(View view) {
        configToolbar();
        configRecyclerView();
        configSwipeContainer();
    }

    private void configToolbar() {
        AppCompatActivity actividad = (AppCompatActivity) getActivity();
        actividad.setSupportActionBar(toolbar);
        actividad.getSupportActionBar().setHomeButtonEnabled(true);
        actividad.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void configRecyclerView() {
        lstAlumnos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        lstAlumnos.setLayoutManager(mLayoutManager);
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    private void configSwipeContainer() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarAlumnos();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        configAdaptador();
        lstAlumnos.setAdapter(mAdaptador);
        checkAdapterIsEmpty();
    }

    private void configAdaptador() {
        if (mAdaptador == null) {
            mAdaptador = new MainAdapter();
            mAdaptador.setOnItemClickListener(this);
            cargarAlumnos();
        }
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
    }

    private void checkAdapterIsEmpty() {
        emptyView.setVisibility(mAdaptador.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void cargarAlumnos() {
        swipeContainer.setRefreshing(true);
        mPresenter.obtenerAlumnos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdaptador.unregisterAdapterDataObserver(mObservador);
        mPresenter.onDestroy();
    }

    @OnClick(R.id.fabAccion)
    void fabAccionOnClick() {
        mPresenter.agregarAlumno();
    }

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        mPresenter.actualizarAlumno(alumno);
    }

    @Override
    public void mostrarListaAlumnos(List<Alumno> alumnos) {
        swipeContainer.setRefreshing(false);
        mAdaptador.changeData(alumnos);
        checkAdapterIsEmpty();
    }

    @Override
    public void navegarNuevoAlumno() {
        mMessageManager.showMessage(lstAlumnos, "Nuevo alumno");
    }

    @Override
    public void navegarAlumno(Alumno alumno) {
        mMessageManager.showMessage(lstAlumnos, "Actualizar alumno");
    }

    @Override
    public void errorObtiendoAlumnos() {
        swipeContainer.setRefreshing(false);
        mMessageManager.showMessage(lstAlumnos, "Error obteniendo alumnos");
    }

}
