package pedrojoya.iessaladillo.es.pr227.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import java.util.Collections;

import pedrojoya.iessaladillo.es.pr227.R;
import pedrojoya.iessaladillo.es.pr227.base.IconicDrawable;
import pedrojoya.iessaladillo.es.pr227.base.LeaveBehindCallback;
import pedrojoya.iessaladillo.es.pr227.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr227.data.local.Database;
import pedrojoya.iessaladillo.es.pr227.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr227.utils.SnackbarUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews();
        viewModel.getStudents().observe(this, students -> {
            if (students != null) {
                Collections.sort(students, (s1, s2) -> s1.getName().compareTo(s2.getName()));
                listAdapter.submitList(students);
            }
        });
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

        lblEmpty.setOnClickListener(v -> addStudent());
        listAdapter = new MainActivityAdapter();
        listAdapter.setOnItemClickListener(
                (view, position) -> showStudent(listAdapter.getItem(position)));
        listAdapter.setEmptyView(lblEmpty);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        LeaveBehindCallback leaveBehindCallback = new LeaveBehindCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                } else {
                    archiveStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                }

            }
        };
        leaveBehindCallback.withRightIconicDrawable(
                new IconicDrawable(ContextCompat.getColor(this, R.color.delete),
                        ContextCompat.getDrawable(this, R.drawable.ic_delete_white_24dp)));
        leaveBehindCallback.withLeftIconicDrawable(
                new IconicDrawable(ContextCompat.getColor(this, R.color.archive),
                        ContextCompat.getDrawable(this, R.drawable.ic_archive_white_24dp)));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(leaveBehindCallback);
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void archiveStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_archive_student, student.getName()));
        deleteStudent(student);
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()));
    }

}
