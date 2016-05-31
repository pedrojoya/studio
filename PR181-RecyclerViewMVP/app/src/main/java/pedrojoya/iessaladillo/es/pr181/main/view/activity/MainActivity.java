package pedrojoya.iessaladillo.es.pr181.main.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pedrojoya.iessaladillo.es.pr181.R;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.presenter.MainPresenter;
import pedrojoya.iessaladillo.es.pr181.main.view.MainView;
import pedrojoya.iessaladillo.es.pr181.main.view.adapter.MainAdapter;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemClickListener;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemLongClickListener;

public class MainActivity extends AppCompatActivity implements MainView, OnMainItemClickListener,
        OnMainItemLongClickListener {

    private static final String STATE_LISTA = "estadoLista";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;

    private MainAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MainPresenter mPresenter;

    // =============
    // Configuración
    // =============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVistas();
        // Se crea el presentador si no existe y se inicializa.
        if (mPresenter == null) {
            mPresenter = new MainPresenter(this);
        }
        mPresenter.initialize();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        configToolbar();
        configRecyclerView();
    }

    // Configura la Toolbar.
    private void configToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Configura el RecyclerView.
    private void configRecyclerView() {
        mAdapter = new MainAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
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
        if (lstAlumnos != null) {
            lstAlumnos.setHasFixedSize(true);
            lstAlumnos.setAdapter(mAdapter);
            checkAdapterIsEmpty();
            mLayoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false);
            lstAlumnos.setLayoutManager(mLayoutManager);
            lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private void checkAdapterIsEmpty() {
        lblNoHayAlumnos.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, Student student, int position) {
        showStudent(student);
    }

    private void showStudent(Student student) {
        Toast.makeText(this, student.getNombre(), Toast.LENGTH_SHORT).show();
    }

    // ============================
    // Interacción con el Presenter
    // ============================

    @Override
    public void onItemLongClick(View view, Student student, int position) {
        mPresenter.removeStudent(position, student);
    }

    @OnClick(R.id.fabAccion)
    public void fabAccionOnClick() {
        mPresenter.addStudent();
    }

    // ===========================
    // Llamadas desde el Presenter
    // ===========================

    @Override
    public void showStudentList(List<Student> students) {
        mAdapter.setData(students);
        checkAdapterIsEmpty();
    }

    @Override
    public void notifyStudentAdded(Student student) {
        mAdapter.addItem(student);
        checkAdapterIsEmpty();
        Toast.makeText(this, R.string.alumno_agregado, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyStudentRemoved(int position, Student student) {
        mAdapter.removeItem(position);
        checkAdapterIsEmpty();
        Toast.makeText(this, R.string.alumno_eliminado, Toast.LENGTH_SHORT).show();
    }

    // ************

}
