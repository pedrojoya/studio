package es.iessaladillo.pedrojoya.pr249.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr249.data.local.Database;

public class ListFragment extends Fragment {

    private TextView lblEmptyView;
    private ListFragmentAdapter listAdapter;
    private ListFragmentViewModel viewModel;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
            new ListFragmentViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            ListFragmentViewModel.class);
        setupViews(requireView());
        observeStudents();
    }

    private void setupViews(@NonNull View view) {
        setupToolbar();
        setupRecyclerView(view);

    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.list_title));
        }
    }

    private void setupRecyclerView(@NonNull View view) {
        RecyclerView lstItems = ViewCompat.requireViewById(view, R.id.lstItems);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new ListFragmentAdapter();
        lstItems.setHasFixedSize(true);
        lstItems.setLayoutManager(new LinearLayoutManager(getContext()));
        lstItems.addItemDecoration(
            new DividerItemDecoration(requireContext(),  DividerItemDecoration.VERTICAL));
        lstItems.setItemAnimator(new DefaultItemAnimator());
        lstItems.setAdapter(listAdapter);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.size() > 0 ? View.INVISIBLE : View.VISIBLE);
        });
    }

}