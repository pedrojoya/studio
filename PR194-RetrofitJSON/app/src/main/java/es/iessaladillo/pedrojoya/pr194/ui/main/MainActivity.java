package es.iessaladillo.pedrojoya.pr194.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.R;
import es.iessaladillo.pedrojoya.pr194.base.Event;
import es.iessaladillo.pedrojoya.pr194.base.RequestState;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import es.iessaladillo.pedrojoya.pr194.data.remote.ApiService;

public class MainActivity extends AppCompatActivity {

    private ListView lstStudents;
    private SwipeRefreshLayout swlPanel;
    private TextView lblEmptyView;

    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(ApiService.getInstance(this).getApi())).get(
                MainActivityViewModel.class);
        initViews();
        viewModel.getStudents().observe(this, request -> {
            if (request != null) {
                if (request instanceof RequestState.Loading) {
                    swlPanel.post(() -> swlPanel.setRefreshing(
                            ((RequestState.Loading) request).isLoading()));
                } else if (request instanceof RequestState.Error) {
                    showErrorLoadingStudents(((RequestState.Error) request).getException());
                } else if (request instanceof RequestState.Result) {
                    //noinspection unchecked
                    showStudents(((RequestState.Result<List<Student>>) request).getData());
                }
            }
        });
    }

    private void findViews() {
        lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        swlPanel = ActivityCompat.requireViewById(this, R.id.swlPanel);
        lblEmptyView = ActivityCompat.requireViewById(this, R.id.lblEmptyView);
    }

    private void initViews() {
        setupListView();
        setupPanel();
    }

    private void setupListView() {
        lstStudents.setEmptyView(lblEmptyView);
        // In order to work well with SwipeRefreshLayout.
        lstStudents.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                int topRowVerticalPosition = (lstStudents == null
                        || lstStudents.getChildCount() == 0) ? 0 : lstStudents.getChildAt(0)
                                                     .getTop();
                swlPanel.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
        // ---
        adapter = new MainActivityAdapter(this, null);
        lstStudents.setAdapter(adapter);
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(() -> viewModel.forceLoadStudents());
    }

    private void showStudents(List<Student> students) {
        adapter.setData(students);
        swlPanel.post(() -> swlPanel.setRefreshing(false));
    }

    private void showErrorLoadingStudents(Event<Exception> event) {
        Exception exception = event.getContentIfNotHandled();
        if (exception != null) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
