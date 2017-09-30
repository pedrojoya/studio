package es.iessaladillo.pedrojoya.pr163.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr163.R;

public class MainActivityPageFragment extends Fragment {

    private static final String ARG_VALUE = "ARG_VALUE";
    private static final String ARG_COLOR = "ARG_COLOR";

    private int mValue;
    private int mColor;

    public MainActivityPageFragment() {
    }

    public static MainActivityPageFragment newInstance(int valor, int colorResId) {
        MainActivityPageFragment fragment = new MainActivityPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VALUE, valor);
        args.putInt(ARG_COLOR, colorResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    private void obtainArguments() {
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_VALUE)) {
                mValue = getArguments().getInt(ARG_VALUE);
            }
            if (getArguments().containsKey(ARG_COLOR)) {
                mColor = ContextCompat.getColor(getContext(), getArguments().getInt(ARG_COLOR));
            } else {
                mColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            }
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
        TextView lblText = view.findViewById(R.id.lblText);
        lblText.setText(getString(R.string.main_activity_section_format, mValue));
        view.setBackgroundColor(mColor);
    }

}

