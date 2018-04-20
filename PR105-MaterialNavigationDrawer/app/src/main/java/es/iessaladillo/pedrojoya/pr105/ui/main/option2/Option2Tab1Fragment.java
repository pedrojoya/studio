package es.iessaladillo.pedrojoya.pr105.ui.main.option2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.data.DB;


@SuppressWarnings("FieldCanBeLocal")
public class Option2Tab1Fragment extends Fragment {

    private RecyclerView lstStudents;
    private FloatingActionButton fab;

    private Option2Tab1Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option2_tab1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        setupFab();
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        adapter = new Option2Tab1Adapter(DB.getAlumnos());
        lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        lstStudents.setHasFixedSize(true);
        lstStudents.setAdapter(adapter);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
    }

    @SuppressWarnings("ConstantConditions")
    private void setupFab() {
        fab = ViewCompat.requireViewById(getParentFragment().getView(), R.id.fab);
        fab.setOnClickListener(v -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(fab, R.string.option2_tab1_fragment_fab_clicked, Snackbar.LENGTH_SHORT)
                .show();
    }

}
