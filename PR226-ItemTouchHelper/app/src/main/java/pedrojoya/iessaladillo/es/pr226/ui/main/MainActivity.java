package pedrojoya.iessaladillo.es.pr226.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr226.R;
import pedrojoya.iessaladillo.es.pr226.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr226.data.local.Database;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr226.utils.SnackbarUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;
    private FloatingActionButton fab;
    private TextView lblEmptyView;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private ItemTouchHelper dragItemTouchHelper;
    private final ItemTouchHelper swipeItemTouchHelper = new ItemTouchHelper(
        new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                @NonNull RecyclerView.ViewHolder viewHolder,
                @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
            new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainActivityViewModel.class);
        int dragDirs =
            getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
            ItemTouchHelper.UP | ItemTouchHelper.DOWN :
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        dragItemTouchHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(dragDirs, 0) {

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                    final int fromPos = viewHolder.getAdapterPosition();
                    final int toPos = target.getAdapterPosition();
                    listAdapter.swapItems(fromPos, toPos);
                    return true;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                }
            });
        setupViews();
        viewModel.getStudents().observe(this, students -> {
            listAdapter.submitList(students);
            lstStudents.smoothScrollToPosition(listAdapter.getItemCount() - 1);
            lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
        viewModel.getSortEnabled().observe(this, enabled -> {
            listAdapter.setSortEnabled(enabled);
            dragItemTouchHelper.attachToRecyclerView(enabled ? lstStudents : null);
            swipeItemTouchHelper.attachToRecyclerView(enabled ? null : lstStudents);
            if (enabled) {
                fab.hide();
            } else {
                fab.show();
            }
        });
    }

    private void setupViews() {
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
        fab = ActivityCompat.requireViewById(this, R.id.fab);
        fab.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmpty);

        lblEmptyView.setOnClickListener(v -> addStudent());
        listAdapter = new MainActivityAdapter();
        listAdapter.setOnItemClickListener((view, position) -> {
            if (!viewModel.isSortEnabled()) {
                showStudent(listAdapter.getItem(position));
            }
        });
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(new GridLayoutManager(this,
            getResources().getInteger(R.integer.main_lstStudents_columns)));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
            getString(R.string.main_click_on_student, student.getName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.mnuSort).setVisible(!viewModel.isSortEnabled());
        menu.findItem(R.id.mnuSaveOrder).setVisible(viewModel.isSortEnabled());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuSort:
                viewModel.toggleSort();
                invalidateOptionsMenu();
                return true;
            case R.id.mnuSaveOrder:
                viewModel.saveOrder();
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}