package es.iessaladillo.pedrojoya.pr216;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import es.iessaladillo.pedrojoya.pr216.utils.FragmentUtils;


public class MainFragment extends Fragment {

    private static final int ANIM_PULL_RIGHT_INDEX = 1;
    private static final String TAG_SECUNDARY_FRAGMENT = "TAG_SECUNDARY_FRAGMENT";

    private Spinner spnExit;
    private Spinner spnEnter;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnNavigate;

    private final int[] mExitAnimationsResIds = {R.anim.push_right, R.anim.push_left, R.anim.push_up, R.anim.push_down, R.anim.scale_down_disappear};
    private final int[] mEnterAnimationsResIds = {R.anim.pull_left, R.anim.pull_right, R.anim.pull_down, R.anim.pull_up, R.anim.scale_up_appear};

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        spnExit = ViewCompat.requireViewById(view, R.id.spnExit);
        spnEnter = ViewCompat.requireViewById(view, R.id.spnEnter);
        btnNavigate = ViewCompat.requireViewById(view, R.id.btnNavigate);

        spnEnter.setSelection(ANIM_PULL_RIGHT_INDEX);
        btnNavigate.setOnClickListener(v -> navigate());
    }

    private void navigate() {
        FragmentUtils.replaceFragmentAddToBackstackWithCustomAnimations(
                requireActivity().getSupportFragmentManager(),
                R.id.flContent,
                SecundaryFragment.newInstance(),
                TAG_SECUNDARY_FRAGMENT,
                TAG_SECUNDARY_FRAGMENT,
                mEnterAnimationsResIds[spnEnter.getSelectedItemPosition()],
                mExitAnimationsResIds[spnExit.getSelectedItemPosition()],
                mEnterAnimationsResIds[spnEnter.getSelectedItemPosition()],
                mExitAnimationsResIds[spnExit.getSelectedItemPosition()]
        );
    }

}
