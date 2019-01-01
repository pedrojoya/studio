package es.iessaladillo.pedrojoya.pr066.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr066.R;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String ARG_OPTION = "ARG_OPTION";

    private String option;

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
        Objects.requireNonNull(getArguments());
        option = getArguments().getString(ARG_OPTION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        TextView lblText = ViewCompat.requireViewById(view, R.id.lblText);
        lblText.setText(option);
    }

}
