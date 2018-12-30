package es.iessaladillo.pedrojoya.pr050.ui.photo;

import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr050.R;
import es.iessaladillo.pedrojoya.pr050.ui.info.InfoFragment;
import es.iessaladillo.pedrojoya.pr050.utils.BitmapUtils;

public class PhotoFragment extends Fragment {

    private PhotoFragmentViewModel viewModel;

    private ImageView imgPhoto;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new PhotoFragmentViewModelFactory(R.id.mnuOriginal))
            .get(PhotoFragmentViewModel.class);
        setupViews(requireView());
        observeEffectId();
    }

    private void setupViews(View view) {
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);

        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.photo_title);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_photo, menu);
        Integer effectId = viewModel.getEffectId().getValue();
        if (effectId != null) {
            menu.findItem(effectId).setChecked(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuInfo:
                navigateToInfo();
                return true;
            case R.id.mnuOriginal:
            case R.id.mnuGrey:
            case R.id.mnuSepia:
            case R.id.mnuBinary:
            case R.id.mnuInverted:
                item.setChecked(true);
                viewModel.setEffectId(item.getItemId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void observeEffectId() {
        viewModel.getEffectId().observe(getViewLifecycleOwner(), this::setCorrectBitmap);
    }

    private void navigateToInfo() {
        requireFragmentManager().beginTransaction().replace(R.id.flContent,
            InfoFragment.newInstance(), InfoFragment.class.getSimpleName()).addToBackStack(
            InfoFragment.class.getSimpleName()).setTransition(
            FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    private void setCorrectBitmap(@IdRes int itemId) {
        switch (itemId) {
            case R.id.mnuOriginal:
                imgPhoto.getDrawable().setColorFilter(null);
                break;
            case R.id.mnuGrey:
                imgPhoto.getDrawable().setColorFilter(
                    new ColorMatrixColorFilter(BitmapUtils.getGreyColorMatrix()));
                break;
            case R.id.mnuSepia:
                imgPhoto.getDrawable().setColorFilter(
                    new ColorMatrixColorFilter(BitmapUtils.getSepiaColorMatrix()));
                break;
            case R.id.mnuBinary:
                imgPhoto.getDrawable().setColorFilter(
                    new ColorMatrixColorFilter(BitmapUtils.getBinaryColorMatrix()));
                break;
            case R.id.mnuInverted:
                imgPhoto.getDrawable().setColorFilter(
                    new ColorMatrixColorFilter(BitmapUtils.getInvertedColorMatrix()));
                break;
        }
    }

}
