package es.iessaladillo.pedrojoya.pr233.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr233.R;

public class MainFragment extends Fragment {

    private static final String ARG_OPTION = "ARG_OPTION";

    private String option;
    private FloatingActionButton fab;

    public static MainFragment newInstance(String option) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            option = getArguments().getString(ARG_OPTION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        TextView lblText = ViewCompat.requireViewById(view, R.id.lblText);
        lblText.setText(option);
        setupFab();
    }

    private void setupFab() {
        fab = requireActivity().findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(v -> Snackbar.make(fab, R.string.main_fragment_clicked_on_fab, Snackbar
                    .LENGTH_SHORT).show());
        }
    }


}
