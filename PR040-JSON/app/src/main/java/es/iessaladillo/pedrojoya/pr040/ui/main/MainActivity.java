package es.iessaladillo.pedrojoya.pr040.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr040.R;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swlPanel;
    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        viewModel.getStudents().observe(this, students -> {
            swlPanel.setRefreshing(false);
            adapter.setData(students);
        });
    }

    private void initViews() {
        ListView lstStudents = findViewById(R.id.lstStudents);
        swlPanel = findViewById(R.id.swlPanel);

        setupListView(lstStudents);
        setupPanel();
    }

    private void setupListView(ListView lstAlumnos) {
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmptyView));
        adapter = new MainActivityAdapter(this, viewModel.getStudents().getValue());
        lstAlumnos.setAdapter(adapter);
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(() -> viewModel.forceLoadStudents());
    }

}
