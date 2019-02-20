package es.iessaldillo.pedrojoya.pr191.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.pedrojoya.pr191.R;
import es.iessaldillo.pedrojoya.pr191.utils.BundleUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private static final String ARG_OPTION_TITLE = "ARG_OPTION_TITLE";
    private static final String ARG_OPTION_MENU_RES_ID = "ARG_OPTION_MENU_RES_ID";
    private static final String ARG_OPTION_ICON_RES_ID = "ARG_OPTION_ICON_RES_ID";

    private String optionTitle;
    @IdRes
    private int optionMenuResId;
    @DrawableRes
    private int optionIconResId;

    private FloatingActionButton fab;

    public MainFragment() {
    }

    static MainFragment newInstance(@IdRes int optionMenuResId, @DrawableRes int optionIconResId,
        String optionTitle) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OPTION_MENU_RES_ID, optionMenuResId);
        args.putInt(ARG_OPTION_ICON_RES_ID, optionIconResId);
        args.putString(ARG_OPTION_TITLE, optionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainArguments();
    }

    private void obtainArguments() {
        optionMenuResId = BundleUtils.requireInt(requireArguments(), ARG_OPTION_MENU_RES_ID);
        optionIconResId = BundleUtils.requireInt(requireArguments(), ARG_OPTION_ICON_RES_ID);
        optionTitle = BundleUtils.requireString(requireArguments(), ARG_OPTION_TITLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
        MainActivityViewModel activityViewModel = ViewModelProviders.of(requireActivity(),
            MainActivityViewModelFactory.getInstance()).get(MainActivityViewModel.class);
        // In order to update the checked menuItem when coming from backstack.
        activityViewModel.setCurrentOption(optionMenuResId);
    }

    private void setupViews(View view) {
        TextView lblOption = ViewCompat.requireViewById(view, R.id.lblOption);
        setupFab();
        lblOption.setText(optionTitle);
    }

    private void setupFab() {
        fab = ActivityCompat.requireViewById(requireActivity(), R.id.fab);

        fab.setOnClickListener(v -> onFabClicked());
        fab.setImageResource(optionIconResId);
    }

    private void onFabClicked() {
        Snackbar.make(fab, getString(R.string.main_fab_clicked, optionTitle),
            Snackbar.LENGTH_SHORT).show();
    }

}
