package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import es.iessaladillo.pedrojoya.pr092.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr092.data.local.Database;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swlPanel;

    private MainFragmentAdapter listAdapter;
    private MainFragmentViewModel viewModel;
    private TextView lblEmptyView;

    public static Fragment newInstance() {
        return new MainFragment();
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
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
            new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeRefreshing();
        observeLogs();
        if (savedInstanceState == null) {
            swlPanel.post(() -> swlPanel.setRefreshing(true));
        }
    }

    private void setupViews(View view) {
        swlPanel = ViewCompat.requireViewById(view, R.id.swipeRefreshLayout);
        setupPanel();
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstList = ViewCompat.requireViewById(view, R.id.lstList);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new MainFragmentAdapter();
        lstList.setHasFixedSize(true);
        lstList.setAdapter(listAdapter);
        lstList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        lstList.addItemDecoration(
            new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupPanel() {
        swlPanel.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeRefreshing() {
        viewModel.getRefreshing().observe(getViewLifecycleOwner(),
            refrehing -> swlPanel.setRefreshing(refrehing));
    }

    private void observeLogs() {
        viewModel.getLogs().observe(getViewLifecycleOwner(), items -> {
            listAdapter.submitList(items);
            lblEmptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuRefresh) {
            viewModel.refresh();
        }
        return super.onOptionsItemSelected(item);
    }

}
