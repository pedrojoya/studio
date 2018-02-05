package pedrojoya.iessaladillo.es.pr201.data.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pedrojoya.iessaladillo.es.pr201.R;
import pedrojoya.iessaladillo.es.pr201.data.model.Student;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;
    private MainActivityAdapter adapter;
    private View emptyView;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        emptyView = findViewById(R.id.lblNoHayAlumnos);
        configToolbar();
        configRecyclerView();
        configFab();
    }

    private void configToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void configFab() {
        FloatingActionButton fabAccion = findViewById(R.id.fabAccion);
        if (fabAccion != null) {
            fabAccion.setOnClickListener(
                    view -> addStudent());
        }
    }

    private void configRecyclerView() {
        adapter = new MainActivityAdapter();
        adapter.setEmptyView(emptyView);
        adapter.setOnItemClickListener(this::onItemClick);
        adapter.setOnItemLongClickListener(this::onItemLongClick);
        lstStudents = findViewById(R.id.lstAlumnos);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        adapter.setList(viewModel.getStudents(false));
    }

    private void addStudent() {
        viewModel.insertStudent();
        adapter.setList(viewModel.getStudents(true));
    }

    private void onItemClick(View view) {
        int position = lstStudents.getChildAdapterPosition(view);
        Student student = adapter.getItem(position);
        Student newStudent = (new Student(student)).reverseName();
        viewModel.updateStudent(student, newStudent);
        adapter.setList(viewModel.getStudents(true));
    }

    @SuppressWarnings("SameReturnValue")
    private boolean onItemLongClick(View view) {
        int position = lstStudents.getChildAdapterPosition(view);
        Student student = adapter.getItem(position);
        viewModel.removeStudent(student);
        adapter.setList(viewModel.getStudents(true));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuOrdenar) {
            adapter.setList(viewModel.toggleOrder());
        }
        return super.onOptionsItemSelected(item);
    }

}
