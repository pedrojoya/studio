package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr105.data.local.Database;


public class Option2Tab1Fragment extends Fragment {

    private FloatingActionButton fab;

    private Option2Tab1Adapter listAdapter;
    private Option2Tab1ViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option2_tab1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new Option2Tab1ViewModelFactory(new RepositoryImpl
                (Database.getInstance()))).get(Option2Tab1ViewModel.class);
        setupViews(requireView());
        observeStudents();
    }

    private void observeStudents() {
        viewModel.getStudents().observe(getViewLifecycleOwner(), students -> listAdapter.submitList(students));
    }

    private void setupViews(View view) {
        setupFab();
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);

        listAdapter = new Option2Tab1Adapter();
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(listAdapter);
        lstStudents.setLayoutManager(new LinearLayoutManager(requireActivity()));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    private void setupFab() {
        fab = ViewCompat.requireViewById(requireParentFragment().requireView(), R.id.fab);

        fab.setOnClickListener(v -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(fab, R.string.option2_tab1_fragment_fab_clicked, Snackbar.LENGTH_SHORT).show();
    }

}
