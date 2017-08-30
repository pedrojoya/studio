package es.iessaladillo.pedrojoya.pr018;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_EFFECT = "STATE_EFFECT";

    private ImageView imgPhoto;

    private MainActivityViewModel mViewModel;
    private int effectId = R.id.mnuOriginal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        restoreSavedInstanceState(savedInstanceState);

    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            effectId = savedInstanceState.getInt(STATE_EFFECT);
            setCorrectBitmap(effectId);

        }
    }

    private void initViews() {
        imgPhoto = findViewById(R.id.imgPhoto);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_EFFECT, effectId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(true);
        }
        if (effectId != 0) {
            menu.findItem(effectId).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (setCorrectBitmap(item.getItemId())) {
            effectId = item.getItemId();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private boolean setCorrectBitmap(@IdRes int itemId) {
        switch (itemId) {
            case R.id.mnuOriginal:
                imgPhoto.setImageBitmap(mViewModel.getOriginalBitmap());
                break;
            case R.id.mnuGrey:
                imgPhoto.setImageBitmap(mViewModel.getGreyBitmap());
                break;
            case R.id.mnuSepia:
                imgPhoto.setImageBitmap(mViewModel.getSepiaBitmap());
                break;
            case R.id.mnuBlue:
                imgPhoto.setImageBitmap(mViewModel.getBlueBitmap());
                break;
            case R.id.mnuGreen:
                imgPhoto.setImageBitmap(mViewModel.getGreenBitmap());
                break;
            default:
                return false;
        }
        return true;
    }

}