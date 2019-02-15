package es.iessaladillo.pedrojoya.pr059.ui.main;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr059.R;
import es.iessaladillo.pedrojoya.pr059.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr059.data.local.Database;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private MainFragmentViewModel viewModel;
    private MainFragmentAdapter listAdapter;

    private RecyclerView lstStudents;
    private SearchView searchView;
    private MenuItem mnuSearch;
    private TextView lblEmptyView;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                new MainFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainFragmentViewModel.class);
        setupViews(getView());
        observeStudents();
    }

    private void setupViews(View view) {
        setupRecyclerView(view);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.size() > 0 ? View.INVISIBLE : View.VISIBLE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main, menu);
        mnuSearch = menu.findItem(R.id.mnuSearch);
        searchView = (SearchView) mnuSearch.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        mnuSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                viewModel.setSearchViewExpanded(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                viewModel.setSearchViewExpanded(false);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter adapter when text is changed
                // listAdapter.getFilter().filter(query);
                viewModel.setSearchQuery(query);
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Restore searching state (in this order).
        String query = viewModel.getSearchQuery();
        if (viewModel.isSearchViewExpanded()) {
            // If done directly, menu item disappears after collapsing.
            lstStudents.post(() -> {
                mnuSearch.expandActionView();
                if (!TextUtils.isEmpty(query)) {
                    searchView.setQuery(query, false);
                }
            });
        }

    }

    private void setupRecyclerView(View view) {
        lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        listAdapter = new MainFragmentAdapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

}
