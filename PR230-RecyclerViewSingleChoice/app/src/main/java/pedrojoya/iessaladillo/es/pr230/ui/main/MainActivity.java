package pedrojoya.iessaladillo.es.pr230.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr230.R;
import pedrojoya.iessaladillo.es.pr230.base.PositionalDetailsLookup;
import pedrojoya.iessaladillo.es.pr230.base.PositionalItemKeyProvider;
import pedrojoya.iessaladillo.es.pr230.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr230.data.local.Database;
import pedrojoya.iessaladillo.es.pr230.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr230.utils.SnackbarUtils;
import pedrojoya.iessaladillo.es.pr230.utils.ToastUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    @SuppressWarnings("FieldCanBeLocal")
    private MainActivityViewModel viewModel;
    private MainActivityListAdapter listAdapter;
    private SelectionTracker<Long> selectionTracker;

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
        // Debe recuperarse el estado del selectionTracker una vez haya sido creado,
        // por lo que no se puede hacer en onRestoreInstanceState().
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
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
        fabAccion.setOnClickListener(view -> showSelectedStudent());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        TextView lblEmpty = ActivityCompat.requireViewById(this, R.id.lblEmpty);

        listAdapter = new MainActivityListAdapter();
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((v, position) -> {
            if (!selectionTracker.hasSelection()) {
                selectionTracker.select((long) position);
            }
        });
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);

        // Creamos el selectionTracker y se lo asignamos al adaptador.
        // DEBE HACERSE SIEMPRE DESPUÉS DE HABER ASIGNADO EL ADAPTADOR AL RECYCLERVIEW.
        setupSelectionTracker();
        listAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>("lstStudentsSelection", lstStudents,
                new PositionalItemKeyProvider(), new PositionalDetailsLookup(lstStudents),
                // Las claves son long.
                StorageStrategy.createLongStorage())
                // Selección simple
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                for (Long key : selectionTracker.getSelection()) {
                    ToastUtils.toast(MainActivity.this,
                            listAdapter.getItem((int) (long) key).getName());
                }
            }
        });
    }

    private void showSelectedStudent() {
        if (selectionTracker.hasSelection()) {
            for (Long key : selectionTracker.getSelection()) {
                showStudent(listAdapter.getItem((int) (long) key));
            }

        } else {
            showNoStudentSelected();
        }
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_student_selected, student.getName()));
    }

    private void showNoStudentSelected() {
        SnackbarUtils.snackbar(lstStudents, getString(R.string.main_activity_no_student_selected));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

}
