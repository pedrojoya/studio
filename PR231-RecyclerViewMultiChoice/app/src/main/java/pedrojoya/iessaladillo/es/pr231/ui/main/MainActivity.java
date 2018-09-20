package pedrojoya.iessaladillo.es.pr231.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Collections;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import pedrojoya.iessaladillo.es.pr231.base.PositionalDetailsLookup;
import pedrojoya.iessaladillo.es.pr231.base.PositionalItemKeyProvider;
import pedrojoya.iessaladillo.es.pr231.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr231.data.local.Database;
import pedrojoya.iessaladillo.es.pr231.utils.SnackbarUtils;
import pedrojoya.iessaladillo.es.pr231.utils.ToastUtils;
import pedrojoya.iessaladillo.es.pr331.R;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    @SuppressWarnings("FieldCanBeLocal")
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
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
        fabAccion.setOnClickListener(view -> showSelectedStudents());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        TextView lblEmpty = ActivityCompat.requireViewById(this, R.id.lblEmpty);

        listAdapter = new MainActivityAdapter();
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((v, position) -> {
            if (!selectionTracker.hasSelection()) {
                selectionTracker.select((long) position);
            }
        });
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
        // Creamos el selectionTracker y se lo asignamos al adaptador.
        // DEBE HACERSE SIEMPRE DESPUÉS DE HABER ASIGNADO EL ADAPTADOR AL RECYCLERVIEW.
        setupSelectionTracker();
        listAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>("my-position-selection", lstStudents,
                new PositionalItemKeyProvider(),
                // StableIdKeyProvider usa el id del item como key.
                //new StableIdKeyProvider(lstStudents),
                new PositionalDetailsLookup(lstStudents),
                // Las claves son long.
                StorageStrategy.createLongStorage()).build();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                int selected = selectionTracker.getSelection().size();
                if (selected > 0) {
                    ToastUtils.toast(MainActivity.this,
                            getResources().getQuantityString(R.plurals.main_activity_selected,
                                    selected, selected));
                }
            }
        });
    }

    private void showSelectedStudents() {
        StringBuilder selectedNames = new StringBuilder();
        for (Long key : selectionTracker.getSelection()) {
            selectedNames.append(listAdapter.getItem((int) (long) key).getName());
            selectedNames.append(", ");
        }
        String message = selectedNames.toString();
        if (!TextUtils.isEmpty(message)) {
            SnackbarUtils.snackbar(lstStudents,
                    getString(R.string.main_activity_students_selected, message));
        } else {
            SnackbarUtils.snackbar(lstStudents,
                    getString(R.string.main_activity_no_students_selected));
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

}
