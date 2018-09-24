package pedrojoya.iessaladillo.es.pr228.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr228.R;
import pedrojoya.iessaladillo.es.pr228.base.LayoutLeaveBehindCallback;
import pedrojoya.iessaladillo.es.pr228.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr228.data.local.Database;
import pedrojoya.iessaladillo.es.pr228.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr228.utils.SnackbarUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
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
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((view, position) -> showStudent(listAdapter.getItem(position)));
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        LayoutLeaveBehindCallback layoutLeaveBehindCallback = new LayoutLeaveBehindCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            protected View getForegroundView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).foreground_view;
            }

            @Override
            protected View getRightLeaveBehindView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).rightLeaveBehind;
            }

            @Override
            protected View getLeftLeaveBehindView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).leftLeaveBehind;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                // No drag-&-drop.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                } else {
                    archiveStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(layoutLeaveBehindCallback);
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
