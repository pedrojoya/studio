package es.iessaldillo.pedrojoya.pr191.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaldillo.pedrojoya.pr191.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String ARG_OPTION = "ARG_OPTION";

    private String option;

    public MainFragment() {
    }

    static MainFragment newInstance(String option) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    private void obtainArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            option = arguments.getString(ARG_OPTION);
        }
        if (arguments == null || option == null) {
            throw new RuntimeException("MainFragment must receive and argument with key ARG_OPTION");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
    }

    private void setupViews(View view) {
        setupToolbar();
        TextView lblOption = ViewCompat.requireViewById(view, R.id.lblOption);
        lblOption.setText(option);
    }

    private void setupToolbar() {
        requireActivity().setTitle(option);
    }

}
