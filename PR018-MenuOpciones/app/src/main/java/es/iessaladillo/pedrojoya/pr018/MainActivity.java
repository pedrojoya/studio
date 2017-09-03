package es.iessaladillo.pedrojoya.pr018;

import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr018.utils.BitmapUtils;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_EFFECT = "STATE_EFFECT";
    private static final String STATE_VISIBLE = "STATE_VISIBLE";

    private ImageView imgPhoto;

    private int mEffectId = R.id.mnuOriginal;
    private boolean mIsVisible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreSavedInstanceState(savedInstanceState);
        initViews();

    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mEffectId = savedInstanceState.getInt(STATE_EFFECT);
            mIsVisible = savedInstanceState.getBoolean(STATE_VISIBLE);
        }
    }

    private void initViews() {
        imgPhoto = findViewById(R.id.imgPhoto);

        imgPhoto.setVisibility(mIsVisible ? View.VISIBLE : View.INVISIBLE);
        setCorrectBitmap(mEffectId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_EFFECT, mEffectId);
        outState.putBoolean(STATE_VISIBLE, mIsVisible);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        menu.findItem(mEffectId).setChecked(true);
        menu.findItem(R.id.mnuVisible).setChecked(mIsVisible);
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
                Toast.makeText(this, R.string.main_activity_info, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuVisible:
                mIsVisible = !mIsVisible;
                item.setChecked(mIsVisible);
                imgPhoto.setVisibility(item.isChecked() ? View.VISIBLE : View.INVISIBLE);
                return true;
            case R.id.mnuEdit:
                Toast.makeText(this, R.string.main_activity_edit, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuDelete:
                Toast.makeText(this, R.string.main_activity_delete, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuOriginal:
            case R.id.mnuGrey:
            case R.id.mnuSepia:
            case R.id.mnuBinary:
            case R.id.mnuInverted:
                mEffectId = item.getItemId();
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