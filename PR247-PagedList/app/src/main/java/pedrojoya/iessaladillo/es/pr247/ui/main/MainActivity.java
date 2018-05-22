package pedrojoya.iessaladillo.es.pr247.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pedrojoya.iessaladillo.es.pr247.R;
import pedrojoya.iessaladillo.es.pr247.data.model.Student;
import pedrojoya.iessaladillo.es.pr247.recycleradapter.OnItemClickListener;
import pedrojoya.iessaladillo.es.pr247.recycleradapter.OnItemLongClickListener;


public class MainActivity extends AppCompatActivity {

    private RecyclerView lstStudents;

    private MainActivityViewModel mViewModel;
    private MainActivityAdapter mAdapter;
    private TextView lblEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
        mViewModel.queryPagedStudents().observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(@Nullable PagedList<Student> students) {
                Log.d("Mia", "Dando datos");
                mAdapter.submitList(students);
                lblEmpty.setVisibility(View.INVISIBLE);
            }
        });
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
        mAdapter = new MainActivityAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(mAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void addStudent() {
        mViewModel.addFakeStudent();
        lstStudents.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

}
