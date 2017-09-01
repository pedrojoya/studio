package es.iessaladillo.pedrojoya.pr092.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        initViews(getView());
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        swlPanel = view.findViewById(R.id.swlPanel);
        lstList = view.findViewById(R.id.lstList);

        setupPanel();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        lstList.setHasFixedSize(true);
        mAdapter = new MainAdapter(mViewModel.getData());
        lstList.setAdapter(mAdapter);
        lstList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));
        lstList.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        lstList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(this::refresh);
    }

    private void refresh() {
        swlPanel.setRefreshing(true);
        // Loading time simulation.
        new Handler().postDelayed(this::addData, SIMULATION_SLEEP_MILI);
    }

    private void addData() {
        mAdapter.addItem(mSimpleDateFormat.format(new Date()));
        swlPanel.setRefreshing(false);
    }

}
