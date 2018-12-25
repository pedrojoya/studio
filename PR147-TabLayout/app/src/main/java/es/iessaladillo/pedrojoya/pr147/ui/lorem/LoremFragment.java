package es.iessaladillo.pedrojoya.pr147.ui.lorem;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import es.iessaladillo.pedrojoya.pr147.R;

public class LoremFragment extends Fragment {

    private FloatingActionButton fab;

    public LoremFragment() {
    }

    public static LoremFragment newInstance() {
        return new LoremFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lorem, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews(getView());
    }

    @SuppressWarnings("unused")
    private void setupViews(View view) {
        // Only first fragment in ViewPager should do this.
        setupFab();
    }

    private void share() {
        Snackbar.make(fab, getString(R.string.lorem_share), Snackbar.LENGTH_SHORT).show();
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
        fab = requireActivity().findViewById(R.id.fab);
        fab.setOnClickListener(v -> share());
    }

}
