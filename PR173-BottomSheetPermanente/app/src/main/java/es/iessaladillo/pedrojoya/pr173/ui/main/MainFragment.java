package es.iessaladillo.pedrojoya.pr173.ui.main;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr173.R;
import es.iessaladillo.pedrojoya.pr173.utils.ToastUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private ImageView imgDetail;

    private MainFragmentViewModel viewModel;
    private BottomSheetBehavior<RelativeLayout> bsb;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int bsbInitialState = BottomSheetBehavior.STATE_COLLAPSED;
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(bsbInitialState))
                .get(MainFragmentViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        RelativeLayout rlPanel = ViewCompat.requireViewById(view, R.id.rlPanel);
        bsb = BottomSheetBehavior.from(rlPanel);
        setupDetailIcon(view);
        setupBottomSheet();
        setupFab(view);
    }

    private void setupDetailIcon(View view) {
        imgDetail = ViewCompat.requireViewById(view, R.id.imgDetail);
        imgDetail.setOnClickListener(v -> expandOrCollapseBottomSheet());
    }

    private void setupFab(View view) {
        FloatingActionButton fab = ViewCompat.requireViewById(view, R.id.fab);
        fab.setOnClickListener(v -> searchDailyPhoto());
    }

    private void setupBottomSheet() {
        //bsb.setPeekHeight(getResources().getDimensionPixelSize(R.dimen
        // .bottomSheet_peekHeight));
        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                viewModel.setBsbState(newState);
                updateIcon(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        bsb.setState(viewModel.getBsbState());
        updateIcon(bsb.getState());
    }

    private void updateIcon(int newState) {
        switch (newState) {
            case BottomSheetBehavior.STATE_EXPANDED:
                imgDetail.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
            case BottomSheetBehavior.STATE_HIDDEN:
                imgDetail.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);
                break;
            default:
        }
    }

    private void searchDailyPhoto() {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, getString(R.string.main_activity_daily_photo));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            ToastUtils.toast(requireContext(), getString(R.string.main_no_activity_available));
        }
    }

    private void expandOrCollapseBottomSheet() {
        if (bsb.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (bsb.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

}
