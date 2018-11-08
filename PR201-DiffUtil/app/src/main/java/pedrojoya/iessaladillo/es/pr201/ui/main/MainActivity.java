package pedrojoya.iessaladillo.es.pr201.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr201.R;
import pedrojoya.iessaladillo.es.pr201.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr201.data.local.Database;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;


public final class MainActivity extends AppCompatActivity {

    private MainActivityAdapter listAdapter;
    private MainActivityViewModel viewModel;
    private TextView lblEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
            new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainActivityViewModel.class);
        setupViews();
        observeStudents();
    }

    private void setupViews() {
        setupToolbar();
        setupRecyclerView();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFab() {
        FloatingActionButton fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmptyView);
        lblEmptyView.setOnClickListener(v -> addStudent());
        listAdapter = new MainActivityAdapter();
        listAdapter.setOnItemClickListener(
            (view, position) -> updateStudent(listAdapter.getItem(position)));
        listAdapter.setOnItemLongClickListener((view, position) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
        RecyclerView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R
            .integer.main_lstStudents_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(this, students -> listAdapter.submitList(students));
        viewModel.isListEmpty().observe(this, empty ->
            lblEmptyView.setVisibility(empty ? View.VISIBLE : View.INVISIBLE));
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
            toggleOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem mnuSort = menu.findItem(R.id.mnuSort);
        boolean desc = viewModel.isInDescendentOrder();
        mnuSort.setIcon(desc ? R.drawable.ic_sort_ascending_white_24dp : R.drawable
            .ic_sort_descending_white_24dp);
        return super.onPrepareOptionsMenu(menu);
    }

    private void toggleOrder() {
        viewModel.toggleOrder();
        invalidateOptionsMenu();
    }

}
