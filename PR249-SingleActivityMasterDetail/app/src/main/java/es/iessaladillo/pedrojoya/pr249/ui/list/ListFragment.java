package es.iessaladillo.pedrojoya.pr249.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

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
import es.iessaladillo.pedrojoya.pr249.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr249.ui.main.MainActivityViewModelFactory;

public class ListFragment extends Fragment {

    private TextView lblEmptyView;
    private ListFragmentAdapter listAdapter;
    private ListFragment.onItemSelectedListener listener;
    private MainActivityViewModel viewModel;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (onItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                activity.toString() + " must implement ListFragment.onItemSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
            new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
            MainActivityViewModel.class);
        Objects.requireNonNull(getView());
        setupViews(getView());
        refreshData();
    }

    private void refreshData() {
        List<String> students = viewModel.getStudents(false);
        listAdapter.submitList(students);
        lblEmptyView.setVisibility(students.size() > 0 ? View.INVISIBLE: View.VISIBLE);
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
        listAdapter.setOnItemClickListener(
            (v, position) -> listener.onItemSelected(listAdapter.getItem(position)));
        lstItems.setHasFixedSize(true);
        lstItems.setLayoutManager(
            new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstItems.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        lstItems.setItemAnimator(new DefaultItemAnimator());
        lstItems.setAdapter(listAdapter);
    }

    public interface onItemSelectedListener {
        void onItemSelected(String item);
    }

}