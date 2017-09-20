package es.iessaladillo.pedrojoya.pr203.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.iessaladillo.pedrojoya.pr203.App;
import es.iessaladillo.pedrojoya.pr203.R;
import es.iessaladillo.pedrojoya.pr203.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr203.data.model.Student;
import es.iessaladillo.pedrojoya.pr203.ui.student.StudentActivity;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lstStudents)
    RecyclerView lstStudents;
    @BindView(R.id.lblEmptyView)
    TextView lblEmptyView;

    private RepositoryImpl repository;
    private MainActivityViewModel viewModel;
    private MainActivityAdapter adapter;
    private DataSubscription studentsSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repository = RepositoryImpl.getInstance(((App) getApplication()).getBoxStore());
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        loadStudents();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab)
    public void addStudent() {
        StudentActivity.start(this);
    }

    private void setupRecyclerView() {
        adapter = new MainActivityAdapter(viewModel.getStudents());
        adapter.setOnItemClickListener((view, student, position) -> showStudent(view, student));
        adapter.setOnItemLongClickListener((view, student, position) -> deleteStudent(student));
        adapter.setEmptyView(lblEmptyView);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadStudents() {
        studentsSubscription = repository.queryStudents().subscribe().on(
                AndroidScheduler.mainThread()).observer(students -> {
            viewModel.setStudents(students);
            adapter.setData(students);
        });
    }

    @Override
    protected void onDestroy() {
        // Cancel subscription to query.
        if (studentsSubscription != null) {
            studentsSubscription.cancel();
        }
        adapter.onDestroy();
        super.onDestroy();
    }

    private void showStudent(View view, Student student) {
        StudentActivity.start(this, student.getId());
    }

    private void deleteStudent(Student student) {
        repository.deleteStudent(student);
    }

}
