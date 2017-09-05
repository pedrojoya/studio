package es.iessaladillo.pedrojoya.pr147.main;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr147.R;

public class LoremFragment extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private FloatingActionButton fab;

    public static LoremFragment newInstance() {
        return new LoremFragment();
    }

    public LoremFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lorem, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initViews(getView());
        }
    }

    @SuppressWarnings("unused")
    private void initViews(View view) {
        fab = getActivity().findViewById(R.id.fab);

        // Only first fragment in ViewPager should do this.
        setupFab();
    }

    private void share() {
        Toast.makeText(getActivity(), getString(R.string.lorem_fragment_share), Toast.LENGTH_SHORT)
                .show();
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
            fab.setImageResource(R.drawable.ic_share_white_24dp);
            fab.setOnClickListener(v -> share());
        }
    }

}
