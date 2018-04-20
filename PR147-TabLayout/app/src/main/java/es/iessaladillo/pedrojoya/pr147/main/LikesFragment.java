package es.iessaladillo.pedrojoya.pr147.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr147.R;

public class LikesFragment extends Fragment {

    private static final String STATE_LIKES = "STATE_LIKES";

    private TextView lblLikes;
    private FloatingActionButton fab;

    private int mLikes;

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
        restoreIntanceState(savedInstanceState);
        if (getView() != null) {
            initViews(getView());
        }
    }

    private void restoreIntanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLikes = savedInstanceState.getInt(STATE_LIKES);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_LIKES, mLikes);
    }

    private void initViews(View view) {
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);
        fab = requireActivity().findViewById(R.id.fab);

        lblLikes.setText(String.valueOf(mLikes));
    }

    private void like() {
        mLikes++;
        lblLikes.setText(String.valueOf(mLikes));
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
