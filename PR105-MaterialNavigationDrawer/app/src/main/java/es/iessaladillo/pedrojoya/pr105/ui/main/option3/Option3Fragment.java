package es.iessaladillo.pedrojoya.pr105.ui.main.option3;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnFragmentShownListener;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;


public class Option3Fragment extends Fragment {

    private OnToolbarAvailableListener onToolbarAvailableListener;
    private OnFragmentShownListener onFragmentShownListener;

    private FloatingActionButton fab;

    public static Option3Fragment newInstance() {
        return new Option3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(requireView());
        // In order to update the checked menuItem when coming from backstack.
        onFragmentShownListener.onFragmentShown(R.id.mnuOption3);
    }

    private void setupViews(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = ViewCompat.requireViewById(view,
            R.id.collapsingToolbar);
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        fab = ViewCompat.requireViewById(view, R.id.fab);

        collapsingToolbarLayout.setTitle(getString(R.string.activity_main_option3));
        toolbar.setTitle(getString(R.string.activity_main_option3));
        onToolbarAvailableListener.onToolbarAvailable(toolbar);
        fab.setOnClickListener(v -> showMessage());
    }

    private void showMessage() {
        Snackbar.make(fab, R.string.detail_activity_fab_clicked, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
        try {
            onToolbarAvailableListener = (OnToolbarAvailableListener) activity;
        } catch (Exception e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnToolbarAvailableListener interface");
        }
        try {
            onFragmentShownListener = (OnFragmentShownListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentShownListener interface");
        }
    }

}
