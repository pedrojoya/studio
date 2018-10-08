package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.iessaladillo.pedrojoya.pr092.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swlPanel;

    private MainAdapter listAdapter;
    private MainFragmentViewModel viewModel;

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
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        initViews(getView());
        viewModel.getData().observe(this, items -> {
            listAdapter.submitList(items);
            swlPanel.setRefreshing(false);
        });
        // Initial data load. With post so the animation works properly.
        if (savedInstanceState == null) {
            swlPanel.post(() -> {
                swlPanel.setRefreshing(true);
                viewModel.refresh();
            });
        }
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View view) {
        swlPanel = ViewCompat.requireViewById(view, R.id.swipeRefreshLayout);

        setupPanel();
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstList = ViewCompat.requireViewById(view, R.id.lstList);
        TextView emptyView = ViewCompat.requireViewById(view, R.id.emptyView);

        listAdapter = new MainAdapter(new ArrayList<>());
        listAdapter.setEmptyView(emptyView);
        lstList.setHasFixedSize(true);
        lstList.setAdapter(listAdapter);
        lstList.setLayoutManager(
                new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        lstList.addItemDecoration(
                new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupPanel() {
        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(() -> viewModel.refresh());
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
            viewModel.refresh();
        }
        return super.onOptionsItemSelected(item);
    }
}
