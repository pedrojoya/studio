package pedrojoya.iessaladillo.es.pr243.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;

import pedrojoya.iessaladillo.es.pr243.R;
import pedrojoya.iessaladillo.es.pr243.actionmode.OnItemClickListener;
import pedrojoya.iessaladillo.es.pr243.data.model.Student;
import pedrojoya.iessaladillo.es.pr243.tracker.ActionModeTrackerListAdapter;


public class MainActivity extends AppCompatActivity implements OnItemClickListener<Student> {

    private static final String SELECTION_ID = "SELECTION_ID";

    private RecyclerView lstStudents;
    private TextView mEmptyView;

    private MainActivityViewModel mViewModel;
    private MainActivityAdapter mAdapter;

    private final ActionModeTrackerListAdapter.MultiChoiceModeListener multiChoiceModeListener = new
            ActionModeTrackerListAdapter.MultiChoiceModeListener() {


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
            return true;
        }

        private void updateSelectedCountDisplay(ActionMode mode) {
            int count = mAdapter.getSelection().size();
            mode.setTitle(getResources().getQuantityString(R.plurals.selected, count, count));
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            updateSelectedCountDisplay(mode);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnuDelete:
                    deleteStudents();
                    return true;
            }
            return false;
        }

        @Override
        public void onSelectionChanged(ActionMode mode, int selected) {
            updateSelectedCountDisplay(mode);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        mEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmpty);

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
        mAdapter = new MainActivityAdapter();
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(mAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        mAdapter.submitList(mViewModel.getStudents(false));
        // Debe hacerse después de asignar el adaptador al recyclerview.
        // mAdapter.buildSelectionTracker(this, multiChoiceModeListener, SELECTION_ID, lstStudents,
        //        Student.class);
    }

    private void addStudent() {
        mViewModel.addStudent();
        mAdapter.submitList(mViewModel.getStudents(true));
        lstStudents.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private void showStudent(Student student) {
        Snackbar.make(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()),
                Snackbar.LENGTH_SHORT).show();
    }


    private void deleteStudents() {
        Iterator iterator = mAdapter.getSelection().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            mViewModel.removeStudent((Student) key);
            mAdapter.deselect(key);
        }
        mAdapter.submitList(mViewModel.getStudents(true));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, Student item, int position, long id) {
        showStudent(item);
    }

}
