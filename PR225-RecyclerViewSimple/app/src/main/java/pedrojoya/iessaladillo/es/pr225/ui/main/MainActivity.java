package pedrojoya.iessaladillo.es.pr225.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr225.R;
import pedrojoya.iessaladillo.es.pr225.base.MessageManager;
import pedrojoya.iessaladillo.es.pr225.base.SnackbarManager;
import pedrojoya.iessaladillo.es.pr225.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr225.data.local.Database;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

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
        loadData();

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
        listAdapter = new MainActivityAdapter();
        RecyclerView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new GridLayoutManager(this, getResources().getInteger(R.integer.main_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    private void loadData() {
        List<Student> students = viewModel.getStudents();
        listAdapter.submitList(students);
        lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void addStudent() {
        messageManager.showMessage(getString(R.string.main_fabClicked));
    }

}
