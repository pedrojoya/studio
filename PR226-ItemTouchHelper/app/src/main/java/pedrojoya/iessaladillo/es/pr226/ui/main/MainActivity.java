package pedrojoya.iessaladillo.es.pr226.ui.main;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pedrojoya.iessaladillo.es.pr226.R;
import pedrojoya.iessaladillo.es.pr226.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr226.data.local.Database;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr226.utils.SnackbarUtils;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        setupViews();
        viewModel.getStudents().observe(this, students -> {
            listAdapter.submitList(students);
            lstStudents.smoothScrollToPosition(listAdapter.getItemCount() - 1);
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
        FloatingActionButton fabAccion = ActivityCompat.requireViewById(this, R.id.fab);
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        TextView lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmpty);

        lblEmptyView.setOnClickListener(v -> addStudent());
        listAdapter = new MainActivityAdapter();
        listAdapter.setEmptyView(lblEmptyView);
        listAdapter.setOnItemClickListener(
                (view, position) -> showStudent(listAdapter.getItem(position)));
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder
                            target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        listAdapter.swapItems(fromPos, toPos);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        viewModel.deleteStudent(
                                listAdapter.getItem(viewHolder.getAdapterPosition()));
                    }
                });
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void addStudent() {
        viewModel.insertStudent(Database.newFakeStudent());
    }

    private void showStudent(Student student) {
        SnackbarUtils.snackbar(lstStudents,
                getString(R.string.main_click_on_student, student.getName()));
    }

}