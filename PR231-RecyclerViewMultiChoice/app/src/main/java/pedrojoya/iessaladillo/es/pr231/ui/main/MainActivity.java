package pedrojoya.iessaladillo.es.pr231.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import pedrojoya.iessaladillo.es.pr231.base.PositionalDetailsLookup;
import pedrojoya.iessaladillo.es.pr231.base.PositionalItemKeyProvider;
import pedrojoya.iessaladillo.es.pr331.R;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;
    private TextView lblEmpty;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private SelectionTracker<Long> selectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
        // Debe recuperarse el estado del selectionTracker una vez haya sido creado,
        // por lo que no se puede hacer en onRestoreInstanceState().
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lblEmpty = ActivityCompat.requireViewById(this, R.id.lblEmpty);

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
        listAdapter.submitList(viewModel.getStudents());
        // Creamos el selectionTracker y se lo asignamos al adaptador.
        // DEBE HACERSE SIEMPRE DESPUÉS DE HABER ASIGNADO EL ADAPTADOR AL RECYCLERVIEW.
        setupSelectionTracker();
        listAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>(
                "my-position-selection",
                lstStudents,
                new PositionalItemKeyProvider(),
                // StableIdKeyProvider usa el id del item como key.
                //new StableIdKeyProvider(lstStudents),
                new PositionalDetailsLookup(lstStudents),
                // Las claves son long.
                StorageStrategy.createLongStorage())
                .build();
    }

    private void showSelectedStudents() {
        StringBuilder selectedNames = new StringBuilder();
        for (Object key : selectionTracker.getSelection()) {
            selectedNames.append(listAdapter.getItem((int) (long) key).getName());
            selectedNames.append(", ");
        }
        String message = selectedNames.toString();
        if (!TextUtils.isEmpty(message)) {
            Snackbar.make(lstStudents, getString(R.string.main_activity_students_selected, message),
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(lstStudents, getString(R.string.main_activity_no_students_selected),
                    Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

}
