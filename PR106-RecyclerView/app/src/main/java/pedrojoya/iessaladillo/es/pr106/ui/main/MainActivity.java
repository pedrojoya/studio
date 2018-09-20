package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import pedrojoya.iessaladillo.es.pr106.R;
import pedrojoya.iessaladillo.es.pr106.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr106.data.local.Database;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr106.utils.SnackbarUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new
                RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupRecyclerView();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFab() {
        FloatingActionButton fabAccion = ActivityCompat.requireViewById(this, R.id.fab);
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        TextView lblEmpty = ActivityCompat.requireViewById(this, R.id.lblEmpty);
        lblEmpty.setOnClickListener(view -> addStudent());
        listAdapter = new MainActivityAdapter(viewModel.getStudents(false));
        listAdapter.setOnItemClickListener((view, position) -> showStudent(listAdapter.getItem(position)));
        listAdapter.setOnItemLongClickListener((view, position) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
        listAdapter.setEmptyView(lblEmpty);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
        listAdapter.submitList(viewModel.getStudents(true));
        lstStudents.smoothScrollToPosition(listAdapter.getItemCount() - 1);
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()));
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
        listAdapter.submitList(viewModel.getStudents(true));
    }

}
