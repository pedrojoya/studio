package es.iessaladillo.pedrojoya.pr105.ui.main.option1;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnFragmentShownListener;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;


public class Option1Fragment extends Fragment {

    private OnToolbarAvailableListener onToolbarAvailableListener;
    private OnFragmentShownListener onFragmentShownListener;
    private View fab;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
        // In order to update the checked menuItem when coming from backstack.
        informActivity();
    }

    private void initViews(View view) {
        setupToolbar(view);
        setupFab(view);
    }

    private void informActivity() {
        onFragmentShownListener.onFragmentShown(R.id.mnuOption1);
    }

    private void setupFab(View view) {
        fab = ViewCompat.requireViewById(view, R.id.fab);
        fab.setOnClickListener(v -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(fab, R.string.option1_fragment_fab_clicked, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void setupToolbar(View view) {
        onToolbarAvailableListener.onToolbarAvailable(ViewCompat.requireViewById(view, R.id.toolbar),
                getString(R.string.activity_main_option1));
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            onToolbarAvailableListener = (OnToolbarAvailableListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar la interfaz OnToolbarAvailableListener");
        }
        try {
            onFragmentShownListener = (OnFragmentShownListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar la interfaz OnFragmentShownListener");
        }
    }

}
