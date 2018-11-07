package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr106.R;
import pedrojoya.iessaladillo.es.pr106.base.MessageManager;
import pedrojoya.iessaladillo.es.pr106.base.SnackbarManager;
import pedrojoya.iessaladillo.es.pr106.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr106.data.local.Database;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private TextView lblEmptyView;
    private MessageManager messageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
            new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainActivityViewModel.class);
        lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmptyView);
        messageManager = new SnackbarManager(lblEmptyView);
        setupViews();
        loadData(false);
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
        FloatingActionButton fabAccion = ActivityCompat.requireViewById(this, R.id.fab);
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter();
        listAdapter.setOnItemClickListener(
            (view, position) -> showStudent(listAdapter.getItem(position)));
        listAdapter.setOnItemLongClickListener((view, position) -> {
            deleteStudent(listAdapter.getItem(position));
            return true;
        });
        RecyclerView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lblEmptyView.setOnClickListener(view -> addStudent());
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(new GridLayoutManager(this,
            getResources().getInteger(R.integer.main_lstStudents_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
        loadData(true);
    }

    private void showStudent(@NonNull Student student) {
        messageManager.showMessage(getString(R.string.main_click_on_student, student.getName()));
    }

    private void deleteStudent(@NonNull Student student) {
        viewModel.deleteStudent(student);
        loadData(true);
    }

    private void loadData(boolean forceLoad) {
        List<Student> students = viewModel.getStudents(forceLoad);
        listAdapter.submitList(students);
        lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

}
