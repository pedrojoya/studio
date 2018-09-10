package pedrojoya.iessaladillo.es.pr228.ui.main;

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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import pedrojoya.iessaladillo.es.pr228.R;
import pedrojoya.iessaladillo.es.pr228.base.LayoutLeaveBehindCallback;
import pedrojoya.iessaladillo.es.pr228.data.local.model.Student;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;
    private TextView lblEmpty;

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
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
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(viewModel.getStudents());
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((view, position) -> showStudent(listAdapter.getItem(position)));
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        LayoutLeaveBehindCallback layoutLeaveBehindCallback = new LayoutLeaveBehindCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            protected View getForegroundView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).foreground_view;
            }

            @Override
            protected View getRightLeaveBehindView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).rightLeaveBehind;
            }

            @Override
            protected View getLeftLeaveBehindView(RecyclerView.ViewHolder viewHolder) {
                return ((MainActivityAdapter.ViewHolder) viewHolder).leftLeaveBehind;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                    RecyclerView.ViewHolder target) {
                // No drag-&-drop.
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteStudent(viewHolder.getAdapterPosition());
                } else {
                    archiveStudent(viewHolder.getAdapterPosition());
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(layoutLeaveBehindCallback);
        itemTouchHelper.attachToRecyclerView(lstStudents);
    }

    private void archiveStudent(int position) {
        Student item = listAdapter.getItem(position);
        Snackbar.make(lstStudents,
                getString(R.string.main_activity_archive_student, item.getName()),
                Snackbar.LENGTH_SHORT).show();
        deleteStudent(position);
    }

    private void deleteStudent(int position) {
        viewModel.deleteStudent(position);
        listAdapter.notifyItemRemoved(position);
    }

    private void addStudent() {
        viewModel.addFakeStudent();
        listAdapter.notifyItemInserted(listAdapter.getItemCount() - 1);
        lstStudents.scrollToPosition(listAdapter.getItemCount() - 1);
    }

    private void showStudent(Student student) {
        Snackbar.make(lstStudents,
                getString(R.string.main_activity_click_on_student, student.getName()),
                Snackbar.LENGTH_SHORT).show();
    }

}
