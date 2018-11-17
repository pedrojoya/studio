package es.iessaladillo.pedrojoya.pr018.ui.main;


import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr018.R;
import es.iessaladillo.pedrojoya.pr018.utils.BitmapUtils;
import es.iessaladillo.pedrojoya.pr018.utils.ToastUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private ImageView imgPhoto;
    private MainFragmentViewModel viewModel;

    public MainFragment() {
    }

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getView());
        viewModel = ViewModelProviders.of(this, new MainFragmentViewModelFactory(R.id.mnuOriginal))
            .get(MainFragmentViewModel.class);
        setupViews(getView());
    }

    private void setupViews(@NonNull View view) {
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);

        imgPhoto.setVisibility(viewModel.isVisible() ? View.VISIBLE : View.INVISIBLE);
        setCorrectBitmap(viewModel.getEffectId());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        menu.findItem(viewModel.getEffectId()).setChecked(true);
        menu.findItem(R.id.mnuVisible).setChecked(viewModel.isVisible());
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (menu.findItem(R.id.mnuInverted).isChecked()) {
            menu.findItem(R.id.mnuActions).setEnabled(false);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuInfo:
                ToastUtils.toast(requireContext(), R.string.main_info);
                return true;
            case R.id.mnuVisible:
                viewModel.toggleVisibility();
                item.setChecked(viewModel.isVisible());
                imgPhoto.setVisibility(viewModel.isVisible() ? View.VISIBLE : View.INVISIBLE);
                return true;
            case R.id.mnuEdit:
                ToastUtils.toast(requireContext(), R.string.main_edit);
                return true;
            case R.id.mnuDelete:
                ToastUtils.toast(requireContext(), R.string.main_delete);
                return true;
            case R.id.mnuOriginal:
            case R.id.mnuGrey:
            case R.id.mnuSepia:
            case R.id.mnuBinary:
            case R.id.mnuInverted:
                viewModel.setEffectId(item.getItemId());
                item.setChecked(true);
                setCorrectBitmap(item.getItemId());
                requireActivity().invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
