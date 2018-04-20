package es.iessaladillo.pedrojoya.pr092.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.iessaladillo.pedrojoya.pr092.R;

public class MainFragment extends Fragment {

    private static final long SIMULATION_SLEEP_MILI = 2000;

    private SwipeRefreshLayout swlPanel;
    private RecyclerView lstList;

    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss",
            Locale.getDefault());
    private MainAdapter mAdapter;
    private MainActivityViewModel mViewModel;
    private TextView emptyView;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews(getView());
        // Initial data load. With post so the animation works properly.
        if (savedInstanceState == null) {
            swlPanel.post(() -> {
                swlPanel.setRefreshing(true);
                refresh();
            });
        }
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        swlPanel = ViewCompat.requireViewById(view, R.id.swipeRefreshLayout);
        lstList = ViewCompat.requireViewById(view, R.id.lstList);
        emptyView = ViewCompat.requireViewById(view, R.id.emptyView);

        setupPanel();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mAdapter = new MainAdapter(mViewModel.getData());
        mAdapter.setEmptyView(emptyView);
        lstList.setHasFixedSize(true);
        lstList.setAdapter(mAdapter);
        lstList.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,
                false));
        lstList.addItemDecoration(
                new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        // Loading time simulation.
        new Handler().postDelayed(this::addData, SIMULATION_SLEEP_MILI);
    }

    private void addData() {
        mAdapter.addItem(mSimpleDateFormat.format(new Date()));
        swlPanel.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuRefresh) {
            swlPanel.setRefreshing(true);
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }
}
