package es.iessaladillo.pedrojoya.pr147.ui.likes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.ui.main.MainActivityViewModel;

public class LikesFragment extends Fragment {

    private TextView lblLikes;
    private FloatingActionButton fab;
    private MainActivityViewModel viewModel;

    public static LikesFragment newInstance() {
        return new LikesFragment();
    }

    public LikesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);
        fab = requireActivity().findViewById(R.id.fab);

        lblLikes.setText(String.valueOf(viewModel.getLikes()));
    }

    private void like() {
        lblLikes.setText(String.valueOf(viewModel.incrementLikes()));
    }

    // Hack to know if the fragment is currently visible in viewpager.
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setupFab();
        }
    }

    private void setupFab() {
        if (fab != null) {
            fab.setImageResource(R.drawable.ic_thumb_up_white_24dp);
            fab.setOnClickListener(v -> like());
        }
    }

}
