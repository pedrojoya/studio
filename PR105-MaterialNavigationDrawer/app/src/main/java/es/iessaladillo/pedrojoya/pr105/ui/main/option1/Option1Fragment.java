package es.iessaladillo.pedrojoya.pr105.ui.main.option1;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;


public class Option1Fragment extends Fragment {

    private OnToolbarAvailableListener listener;
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
    }

    private void initViews(View view) {
        setupToolbar(view);
        setupFab(view);
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
        listener.onToolbarAvailable(ViewCompat.requireViewById(view, R.id.toolbar),
                getString(R.string.activity_main_option1));
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (OnToolbarAvailableListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar la interfaz OnToolbarAvailableListener");
        }
    }

}
