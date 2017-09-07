package es.iessaldillo.pedrojoya.pr191;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {

    private static final String ARG_OPTION = "ARG_OPTION";

    private String mOption;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView lblOption;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        lblOption = view.findViewById(R.id.lblOption);

        lblOption.setText(mOption);
    }

}
