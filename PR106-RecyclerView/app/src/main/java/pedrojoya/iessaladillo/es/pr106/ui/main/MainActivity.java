package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import pedrojoya.iessaladillo.es.pr106.R;
import pedrojoya.iessaladillo.es.pr106.data.model.Student;


public class MainActivity extends AppCompatActivity implements MainActivityAdapter
        .OnItemClickListener, MainActivityAdapter.OnItemLongClickListener {

    private RecyclerView lstStudents;
    private TextView mEmptyView;

    private MainActivityViewModel mViewModel;
    private MainActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(
                MainActivityViewModel.class);
        initViews();
    }

    private void initViews() {
        lstStudents = findViewById(R.id.lstStudents);
        mEmptyView = findViewById(R.id.lblEmpty);

        setupToolbar();
        setupRecyclerView();
        setupFab();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFab() {
        FloatingActionButton fabAccion = findViewById(R.id.fab);
        fabAccion.setOnClickListener(view -> addStudent());
    }

    private void setupRecyclerView() {
        mAdapter = new MainActivityAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setData(mViewModel.getStudents());
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(mAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void addStudent() {
        mViewModel.addFakeStudent();
        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
        lstStudents.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onItemClick(View view, Student student, int position) {
        Snackbar.make(lstStudents, getString(R.string.main_activity_click_on_student, student.getName()),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, Student student, int position) {
        mAdapter.removeItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.onDestroy();
    }

}
