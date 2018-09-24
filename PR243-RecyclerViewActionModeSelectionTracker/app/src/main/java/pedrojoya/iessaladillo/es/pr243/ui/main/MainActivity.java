package pedrojoya.iessaladillo.es.pr243.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr243.R;
import pedrojoya.iessaladillo.es.pr243.base.MultiChoiceModeListener;
import pedrojoya.iessaladillo.es.pr243.base.PositionalDetailsLookup;
import pedrojoya.iessaladillo.es.pr243.base.PositionalItemKeyProvider;
import pedrojoya.iessaladillo.es.pr243.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr243.data.local.Database;
import pedrojoya.iessaladillo.es.pr243.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr243.utils.SnackbarUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private SelectionTracker<Long> selectionTracker;
    private ActionMode actionMode;
    private final MultiChoiceModeListener multiChoiceModeListener = new MultiChoiceModeListener() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            viewModel.setInActionMode(true);
            actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            updateSelectedCountDisplay(actionMode);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuDelete:
                    deleteStudents();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            selectionTracker.clearSelection();
            viewModel.setInActionMode(false);
        }

        private void updateSelectedCountDisplay(ActionMode actionMode) {
            int count = selectionTracker.getSelection().size();
            actionMode.setTitle(getResources().getQuantityString(R.plurals.selected, count, count));
        }

        @Override
        public void onSelectionChanged(ActionMode actionMode, int selected) {
            updateSelectedCountDisplay(actionMode);
        }
    };

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
        if (viewModel.isInActionMode()) {
            actionMode = startSupportActionMode(multiChoiceModeListener);
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
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        TextView lblEmpty = ActivityCompat.requireViewById(this, R.id.lblEmpty);

        lblEmpty.setOnClickListener(v -> addStudent());
        listAdapter = new MainActivityAdapter();
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener(
                (v, position) -> showStudent(listAdapter.getItem(position)));
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
        selectionTracker = new SelectionTracker.Builder<>("my-position-selection", lstStudents,
                new PositionalItemKeyProvider(), new PositionalDetailsLookup(lstStudents),
                StorageStrategy.createLongStorage()).build();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if (actionMode != null) {
                    if (selectionTracker.hasSelection()) {
                        // Se informa de que ha cambiado la selección.
                        multiChoiceModeListener.onSelectionChanged(actionMode,
                                selectionTracker.getSelection().size());
                    } else {
                        // Si no hay selección se finaliza el actionMode.
                        actionMode.finish();
                        actionMode = null;
                    }
                } else {
                    // Si hay selección, se inicia el actionMode.
                    if (selectionTracker.hasSelection()) {
                        actionMode = startSupportActionMode(multiChoiceModeListener);
                    }
                }
            }
        });
    }

    private void addStudent() {
        if (selectionTracker != null) {
            selectionTracker.clearSelection();
        }
        viewModel.insertStudent(Database.newFakeStudent());
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()));
    }


    private void deleteStudents() {
        ArrayList<Student> studentsToBeDeleted = new ArrayList<>();
        for (Object key : selectionTracker.getSelection()) {
            studentsToBeDeleted.add(listAdapter.getItem((int) (long) key));
        }
        viewModel.deleteStudents(studentsToBeDeleted);
        selectionTracker.clearSelection();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

}
