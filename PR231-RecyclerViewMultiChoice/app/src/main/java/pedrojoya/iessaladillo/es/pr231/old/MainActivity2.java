package pedrojoya.iessaladillo.es.pr231.old;

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

import pedrojoya.iessaladillo.es.pr231.ui.main.MainActivityViewModel;
import pedrojoya.iessaladillo.es.pr231.ui.main.MainActivityViewModelFactory;
import pedrojoya.iessaladillo.es.pr331.R;


public class MainActivity2 extends AppCompatActivity {

    private RecyclerView lstStudents;
    private TextView mEmptyView;

    private MainActivityViewModel mViewModel;
    private MainActivityAdapter2 mAdapter;

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
        fabAccion.setOnClickListener(view -> showSelectedStudents());
    }

    private void setupRecyclerView() {
        mAdapter = new MainActivityAdapter2();
        mAdapter.setEmptyView(mEmptyView);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(mAdapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        mAdapter.submitList(mViewModel.getStudents());
    }

    private void showSelectedStudents() {
        StringBuilder selectedNames = new StringBuilder();
        mAdapter.visitChecks(position -> {
            selectedNames.append(mAdapter.getItem(position).getName());
            selectedNames.append(", ");
        });
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
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }

}
