package es.iessaladillo.pedrojoya.pr123.main;

import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.utils.BitmapUtils;

public class PhotoFragment extends Fragment {

    private static final String STATE_EFFECT = "STATE_EFFECT";

    private ImageView imgPhoto;

    private int mEffectId = R.id.mnuOriginal;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        restoreInstanceState(savedInstanceState);
        initViews(getView());
        super.onActivityCreated(savedInstanceState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mEffectId = savedInstanceState.getInt(STATE_EFFECT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_EFFECT, mEffectId);
    }

    private void initViews(View view) {
        imgPhoto = view.findViewById(R.id.imgPhoto);

        setCorrectBitmap(mEffectId);
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
                showPopupMenu(getActivity().findViewById(R.id.mnuFilter));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showPopupMenu(View v) {
        // Context NOT from toolbar.
        PopupMenu popup = new PopupMenu(imgPhoto.getContext(), v);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.fragment_photo_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onPopupMenuItemClick);
        popup.getMenu().findItem(mEffectId).setChecked(true);
        popup.show();
    }

    @SuppressWarnings("SameReturnValue")
    private boolean onPopupMenuItemClick(MenuItem item) {
        mEffectId = item.getItemId();
        item.setChecked(true);
        setCorrectBitmap(item.getItemId());
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
