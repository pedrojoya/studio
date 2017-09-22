package es.iessaladillo.pedrojoya.pr083.main;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr083.R;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<ArrayList<Student>> {

    private static final int LOADER_ID = 1;
    private static final String DATA_URL =
            "http://www.informaticasaladillo.es/datos.json";

    private SwipeRefreshLayout swlPanel;
    private MainActivityAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void initViews() {
        ListView lstStudents = findViewById(R.id.lstStudents);
        swlPanel = findViewById(R.id.swlPanel);

        setupListView(lstStudents);
        setupPanel();
    }

    private void setupListView(ListView lstAlumnos) {
        lstAlumnos.setEmptyView(findViewById(R.id.lblEmptyView));
        adapter = new MainActivityAdapter(this, new ArrayList<>());
        lstAlumnos.setAdapter(adapter);
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this::loadStudents);
    }

    private Loader<ArrayList<Student>> loadStudents() {
        return getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
    }

    @Override
    public Loader<ArrayList<Student>> onCreateLoader(int id, Bundle args) {
        Log.d(getString(R.string.app_name), "onCreateLoader");
        return new StudentsLoader(this, DATA_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Student>> loader, ArrayList<Student> data) {
        swlPanel.setRefreshing(false);
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Student>> loader) {
        Log.d(getString(R.string.app_name), "onLoaderReset");
        adapter.setData(null);
    }

}
