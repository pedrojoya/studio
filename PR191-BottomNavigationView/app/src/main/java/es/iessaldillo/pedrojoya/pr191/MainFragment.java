package es.iessaldillo.pedrojoya.pr191;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private static final String ARG_OPTION = "ARG_OPTION";

    private String mOption;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView lblOption;
    private FloatingActionButton fab;

    public MainFragment() {
    }

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
        obtainArguments();
    }

    private void obtainArguments() {
        if (getArguments() != null) {
            mOption = getArguments().getString(ARG_OPTION);
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
        initViews(getView());
    }

    private void initViews(View view) {
        lblOption = ViewCompat.requireViewById(view, R.id.lblOption);
        lblOption.setText(mOption);
        setupFab();
    }

    private void setupFab() {
        fab = requireActivity().findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(v -> Snackbar.make(fab, "Me han pulsado", Snackbar
                    .LENGTH_SHORT).show());
            setFabIcon();
        }
    }

    private void setFabIcon() {
        if (TextUtils.equals(mOption, getString(R.string.main_activity_calendar))) {
            fab.setImageResource(R.drawable.ic_access_time_black_24dp);
        } else if (TextUtils.equals(mOption, getString(R.string.main_activity_favorites))) {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_audiotrack_black_24dp);
        }
    }

}
