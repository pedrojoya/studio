package es.iessaladillo.pedrojoya.pr018.ui.main;

import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr018.R;
import es.iessaladillo.pedrojoya.pr018.utils.BitmapUtils;
import es.iessaladillo.pedrojoya.pr018.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private MainActivityViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();

    }

    private void initViews() {
        imgPhoto = ActivityCompat.requireViewById(this, R.id.imgPhoto);

        imgPhoto.setVisibility(viewModel.isVisible() ? View.VISIBLE : View.INVISIBLE);
        setCorrectBitmap(viewModel.getEffectId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        menu.findItem(viewModel.getEffectId()).setChecked(true);
        menu.findItem(R.id.mnuVisible).setChecked(viewModel.isVisible());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.findItem(R.id.mnuInverted).isChecked()) {
            menu.findItem(R.id.mnuActions).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuInfo:
                ToastUtils.toast(this, R.string.main_activity_info);
                return true;
            case R.id.mnuVisible:
                viewModel.toggleVisibility();
                item.setChecked(viewModel.isVisible());
                imgPhoto.setVisibility(viewModel.isVisible() ? View.VISIBLE : View.INVISIBLE);
                return true;
            case R.id.mnuEdit:
                ToastUtils.toast(this, R.string.main_activity_edit);
                return true;
            case R.id.mnuDelete:
                ToastUtils.toast(this, R.string.main_activity_delete);
                return true;
            case R.id.mnuOriginal:
            case R.id.mnuGrey:
            case R.id.mnuSepia:
            case R.id.mnuBinary:
            case R.id.mnuInverted:
                viewModel.setEffectId(item.getItemId());
                item.setChecked(true);
                setCorrectBitmap(item.getItemId());
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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