package es.iessaladillo.pedrojoya.pr147.ui.lorem;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr147.R;
import es.iessaladillo.pedrojoya.pr147.utils.ToastUtils;

public class LoremFragment extends Fragment {

    private FloatingActionButton fab;

    public static LoremFragment newInstance() {
        return new LoremFragment();
    }

    public LoremFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lorem, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    @SuppressWarnings("unused")
    private void initViews(View view) {
        fab = requireActivity().findViewById(R.id.fab);

        // Only first fragment in ViewPager should do this.
        setupFab();
    }

    private void share() {
        ToastUtils.toast(getContext(), getString(R.string.lorem_fragment_share));
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
