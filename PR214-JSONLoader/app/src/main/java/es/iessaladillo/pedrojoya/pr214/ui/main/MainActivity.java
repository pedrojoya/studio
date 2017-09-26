package es.iessaladillo.pedrojoya.pr214.ui.main;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr214.R;
import es.iessaladillo.pedrojoya.pr214.data.remote.StudentsRequest;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<StudentsRequest> {

    private static final int LOADER_ID = 1;
    private static final String DATA_URL =
            "http://www.informaticasaladillo.es/datos.json";
    private static final int TIMEOUT = 5000;

    private SwipeRefreshLayout swlPanel;
    private MainActivityAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if(savedInstanceState == null) {
            swlPanel.post(() -> swlPanel.setRefreshing(true));
        }
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

    private void loadStudents() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    private void showErrorLoadingStudents() {
        Toast.makeText(this, getString(R.string.main_activity_error_loading_students),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader<StudentsRequest> onCreateLoader(int id, Bundle args) {
        return new StudentsLoader(this, DATA_URL, TIMEOUT);
    }

    @Override
    public void onLoadFinished(Loader<StudentsRequest> loader, StudentsRequest data) {
        swlPanel.setRefreshing(false);
        if (data.getError() != null) {
            showErrorLoadingStudents();
            // In order not to be displayed again on orientation change.
            data.removeError();
        } else {
            adapter.setData(data.getStudents());
        }
    }

    @Override
    public void onLoaderReset(Loader<StudentsRequest> loader) {
        //adapter.setData(null);
    }

}
