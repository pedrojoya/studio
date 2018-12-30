package es.iessaldillo.pedrojoya.pr191.ui.main;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaldillo.pedrojoya.pr191.R;
import es.iessaldillo.pedrojoya.pr191.base.OnFragmentShownListener;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String ARG_OPTION_TITLE = "ARG_OPTION_TITLE";
    private static final String ARG_OPTION_MENU_RES_ID = "ARG_OPTION_MENU_RES_ID";

    private String optionTitle;
    private int optionMenuResId;
    private OnFragmentShownListener onFragmentShownListener;

    private FloatingActionButton fab;

    public MainFragment() {
    }

    static MainFragment newInstance(@IdRes int optionMenuResId, String optionTitle) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OPTION_MENU_RES_ID, optionMenuResId);
        args.putString(ARG_OPTION_TITLE, optionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    public void setOnFragmentShownListener(OnFragmentShownListener onFragmentShownListener) {
        this.onFragmentShownListener = onFragmentShownListener;
    }

    @Override
    public void onDetach() {
        onFragmentShownListener = null;
        super.onDetach();
    }

    private void obtainArguments() {
        if (getArguments() != null) {
            optionMenuResId = getArguments().getInt(ARG_OPTION_MENU_RES_ID);
            optionTitle = getArguments().getString(ARG_OPTION_TITLE);
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
        // In order to update the checked menuItem when coming from backstack.
        informActivity();
    }

    private void setupViews(View view) {
        TextView lblOption = ViewCompat.requireViewById(view, R.id.lblOption);
        lblOption.setText(optionTitle);
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
        if (TextUtils.equals(optionTitle, getString(R.string.main_activity_calendar))) {
            fab.setImageResource(R.drawable.ic_access_time_black_24dp);
        } else if (TextUtils.equals(optionTitle, getString(R.string.main_activity_favorites))) {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_audiotrack_black_24dp);
        }
    }

    private void informActivity() {
        onFragmentShownListener.onFragmentShown(optionMenuResId);
    }

}
