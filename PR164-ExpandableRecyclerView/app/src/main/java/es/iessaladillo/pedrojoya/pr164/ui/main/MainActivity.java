package es.iessaladillo.pedrojoya.pr164.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.iessaladillo.pedrojoya.pr164.R;

public class MainActivity extends AppCompatActivity {

    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;
    private RecyclerView lstStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupRecyclerView() {
        adapter = new MainActivityAdapter();
        adapter.addGroups(viewModel.getGroups());
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

}
