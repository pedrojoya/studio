package es.iessaladillo.pedrojoya.pr147.ui.likes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr147.R;

public class LikesFragment extends Fragment {

    private TextView lblLikes;
    private FloatingActionButton fab;
    private LikesFragmentViewModel viewModel;

    public LikesFragment() {
    }

    public static LikesFragment newInstance() {
        return new LikesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LikesFragmentViewModel.class);
        setupViews(requireView());
    }

    private void setupViews(View view) {
        fab = ActivityCompat.requireViewById(requireActivity(), R.id.fab);
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);
        lblLikes.setText(String.valueOf(viewModel.getLikes()));
    }

    private void like() {
        lblLikes.setText(String.valueOf(viewModel.incrementLikes()));
    }

    // Hack to know if the fragment is currently visible in viewpager.
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            setupFab();
        }
    }

    private void setupFab() {
        fab.setOnClickListener(v -> like());
    }

}
