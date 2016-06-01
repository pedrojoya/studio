package pedrojoya.iessaladillo.es.pr181.main.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pedrojoya.iessaladillo.es.pr181.R;
import pedrojoya.iessaladillo.es.pr181.component.UITemporaryMessanger;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.presenter.MainPresenter;
import pedrojoya.iessaladillo.es.pr181.main.view.MainView;
import pedrojoya.iessaladillo.es.pr181.main.view.adapter.MainAdapter;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemClickListener;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemLongClickListener;

public class MainActivity extends AppCompatActivity implements MainView, OnMainItemClickListener,
        OnMainItemLongClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstAlumnos)
    RecyclerView lstAlumnos;
    @BindView(R.id.lblNoHayAlumnos)
    TextView lblNoHayAlumnos;
    @BindView(R.id.fabAccion)
    FloatingActionButton fabAccion;
    @BindView(R.id.swlPanel)
    SwipeRefreshLayout swlPanel;

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
        if (mPresenter == null) {
            mPresenter = new MainPresenter(this);
        }
    }

    private void initVistas() {
        configToolbar();
        configRecyclerView();
        configSwipeRefreshLayout();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

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

    private void configSwipeRefreshLayout() {
        swlPanel.setColorSchemeColors(ContextCompat.getColor(this, R.color.accent));
        swlPanel.setOnRefreshListener(this);
    }

    private void checkAdapterIsEmpty() {
        lblNoHayAlumnos.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, Student student, int position) {
        showStudent(student);
    }

    private void showStudent(Student student) {
        UITemporaryMessanger.showMessage(lstAlumnos, student.getNombre());
    }

    // ============================
    // Interacción con el Presenter
    // ============================

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onItemLongClick(View view, Student student, int position) {
        mPresenter.removeStudent(position, student);
    }

    @OnClick(R.id.fabAccion)
    public void fabAccionOnClick() {
        mPresenter.addStudent();
    }

    @Override
    public void onRefresh() {
        mPresenter.getStudents();
    }

    // ===================================
    // Métodos llamados desde el Presenter
    // ===================================

    @Override
    public void showStudentList(List<Student> students) {
        mAdapter.setData(students);
        checkAdapterIsEmpty();
    }

    @Override
    public void notifyStudentAdded(Student student) {
        mAdapter.addItem(student);
        checkAdapterIsEmpty();
        UITemporaryMessanger.showMessage(lstAlumnos, getString(R.string.alumno_agregado));
    }

    @Override
    public void notifyStudentRemoved(int position, Student student) {
        mAdapter.removeItem(position);
        checkAdapterIsEmpty();
        UITemporaryMessanger.showMessage(lstAlumnos, getString(R.string.alumno_eliminado));
    }

    @Override
    public void showLoading() {
        swlPanel.post(new Runnable() {
            @Override
            public void run() {
                swlPanel.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        swlPanel.setRefreshing(false);
    }

}
