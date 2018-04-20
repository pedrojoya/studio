package es.iessaladillo.pedrojoya.pr105.ui.main.option3;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.base.OnToolbarAvailableListener;


public class Option3Fragment extends Fragment {

    private OnToolbarAvailableListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_option3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        setupToolbar(view);
        setupCollapsingToolbar(view);
    }

    private void setupCollapsingToolbar(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = ViewCompat.requireViewById(view,
                R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.activity_main_option3));
    }

    private void setupToolbar(View view) {
        listener.onToolbarAvailable(ViewCompat.requireViewById(view, R.id.toolbar),
                getString(R.string.activity_main_option3));
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
