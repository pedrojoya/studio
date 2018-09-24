package pedrojoya.iessaladillo.es.pr229.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pedrojoya.iessaladillo.es.pr229.R;
import pedrojoya.iessaladillo.es.pr229.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr229.data.local.Database;
import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;


public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView lstStudents;

    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;
    private List<Student> currentStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new RepositoryImpl(Database
                .getInstance()))).get(MainActivityViewModel.class);
        initViews();
        viewModel.queryStudents().observe(this, students -> {
            currentStudents = students;
            if (currentStudents != null) {
                List<Student> orderedList = new ArrayList<>(currentStudents);
                Collections.sort(orderedList,
                        (student1, student2) -> viewModel.getOrder() * student1.getName().compareTo(student2.getName()));
                adapter.submitList(orderedList);
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
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        TextView emptyView = ActivityCompat.requireViewById(this, R.id.emptyView);
        emptyView.setOnClickListener(v -> addStudent());
        adapter = new MainActivityAdapter();
        adapter.setEmptyView(emptyView);
        adapter.setOnItemClickListener((view, position) ->
                updateStudent(adapter.getItem(position)));
        adapter.setOnItemLongClickListener((view, position) -> {
            deleteStudent(adapter.getItem(position));
            return true;
        });
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(adapter);
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
    }

    private void updateStudent(Student student) {
        Student newStudent = (new Student(student)).reverseName();
        viewModel.updateStudent(student, newStudent);
    }

    private void deleteStudent(Student student) {
        viewModel.deleteStudent(student);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSort) {
            viewModel.toggleOrder();
            if (currentStudents != null) {
                List<Student> orderedList = new ArrayList<>(currentStudents);
                Collections.sort(orderedList,
                        (student1, student2) -> viewModel.getOrder() * student1.getName().compareTo(student2.getName()));
                adapter.submitList(orderedList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
