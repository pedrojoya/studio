package es.iessaladillo.pedrojoya.pr123.ui.photo;

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
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr123.ui.main.MainActivityViewModelFactory;
import es.iessaladillo.pedrojoya.pr123.utils.BitmapUtils;

public class PhotoFragment extends Fragment {

    private ImageView imgPhoto;
    private MainActivityViewModel viewModel;

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
        viewModel = ViewModelProviders.of(requireActivity(), new MainActivityViewModelFactory(R.id
                .mnuOriginal))
                .get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);

        setCorrectBitmap(viewModel.getEffectResId());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_photo, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado realizo la acción deseada.
        switch (item.getItemId()) {
            case R.id.mnuFilter:
                showPopupMenu(requireActivity().findViewById(R.id.mnuFilter));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showPopupMenu(View v) {
        // DON'T USE Context from toolbar.
        PopupMenu popup = new PopupMenu(imgPhoto.getContext(), v);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.fragment_photo_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onPopupMenuItemClick);
        popup.getMenu().findItem(viewModel.getEffectResId()).setChecked(true);
        popup.show();
    }

    @SuppressWarnings("SameReturnValue")
    private boolean onPopupMenuItemClick(MenuItem item) {
        viewModel.setEffectResId(item.getItemId());
        item.setChecked(true);
        setCorrectBitmap(item.getItemId());
        viewModel.setEffectResId(item.getItemId());
        return true;
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
