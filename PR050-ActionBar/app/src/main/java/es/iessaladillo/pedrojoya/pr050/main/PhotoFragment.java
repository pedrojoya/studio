package es.iessaladillo.pedrojoya.pr050.main;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.iessaladillo.pedrojoya.pr050.R;
import es.iessaladillo.pedrojoya.pr050.utils.BitmapUtils;

public class PhotoFragment extends Fragment {

    private static final String STATE_EFFECT = "STATE_EFFECT";

    // Communication interface.
    public interface Callback {
        void onInfoClicked();
    }

    private ImageView imgPhoto;

    private int mEffectId;
    private Callback mListener;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mEffectId = R.id.mnuOriginal;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement PhotoFragment.Callback");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreInstanceState(savedInstanceState);
        initViews(getView());
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mEffectId = savedInstanceState.getInt(STATE_EFFECT);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_EFFECT, mEffectId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_photo, menu);
        menu.findItem(mEffectId).setChecked(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuInfo:
                mListener.onInfoClicked();
                return true;
            case R.id.mnuOriginal:
            case R.id.mnuGrey:
            case R.id.mnuSepia:
            case R.id.mnuBinary:
            case R.id.mnuInverted:
                mEffectId = item.getItemId();
                item.setChecked(true);
                setCorrectBitmap(item.getItemId());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews(View view) {
        imgPhoto = ViewCompat.requireViewById(view, R.id.imgPhoto);

        setCorrectBitmap(mEffectId);
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
