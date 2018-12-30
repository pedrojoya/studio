package es.iessaladillo.pedrojoya.pr092.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

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
import es.iessaladillo.pedrojoya.pr092.base.Event;
import es.iessaladillo.pedrojoya.pr092.base.RequestState;
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
        viewModel = ViewModelProviders.of(this,
            new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainFragmentViewModel.class);
        setupViews(requireView());
        observeData();
        observeRefreshState();
        // Initial data load. With post so the animation works properly.
        if (savedInstanceState == null) {
            swlPanel.post(() -> viewModel.refresh());
        }
        super.onActivityCreated(savedInstanceState);
    }

    private void observeRefreshState() {
        viewModel.getRefreshState().observe(getViewLifecycleOwner(), requestState -> {
            if (requestState instanceof RequestState.Loading) {
                swlPanel.setRefreshing(true);
            } else if (requestState instanceof RequestState.Error) {
                //noinspection unchecked
                showError((RequestState.Error) requestState);
                swlPanel.setRefreshing(false);
            } else {
                swlPanel.setRefreshing(false);
            }
        });
    }

    private void observeData() {
        viewModel.getData().observe(getViewLifecycleOwner(), items -> {
            listAdapter.submitList(items);
            lblEmptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void showError(RequestState.Error<Exception> requestState) {
        Event<Exception> exceptionEvent = requestState.getException();
        Exception exception = exceptionEvent.getContentIfNotHandled();
        if (exception != null) {
            Snackbar.make(lblEmptyView, exception.getMessage(), Snackbar.LENGTH_SHORT).show();
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
        lstList.setLayoutManager(
            new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        lstList.addItemDecoration(
            new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        lstList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupPanel() {
        swlPanel.setOnRefreshListener(() -> viewModel.refresh());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuRefresh) {
            swlPanel.setRefreshing(true);
            viewModel.refresh();
        }
        return super.onOptionsItemSelected(item);
    }

}
