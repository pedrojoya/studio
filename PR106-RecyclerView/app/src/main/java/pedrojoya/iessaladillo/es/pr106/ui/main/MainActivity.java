package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
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
        setupViews();
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
        /* Done in layout xml
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        */
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
        listAdapter.submitList(viewModel.getStudents(true));
        lstStudents.smoothScrollToPosition(listAdapter.getItemCount() - 1);
    }

    private void showStudent(@NonNull Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()));
    }

    private void deleteStudent(@NonNull Student student) {
        viewModel.deleteStudent(student);
        listAdapter.submitList(viewModel.getStudents(true));
    }

}
