package es.iessaladillo.pedrojoya.pr123.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.utils.FragmentUtils;
import es.iessaladillo.pedrojoya.pr123.utils.ToolbarSpinnerAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PHOTO_OPTION_POSITION = 0;
    private static final int INFO_OPTION_POSITION = 1;

    private static final String TAG_PHOTO_FRAGMENT = "TAG_PHOTO_FRAGMENT";
    private static final String TAG_INFO_FRAGMENT = "TAG_INFO_FRAGMENT";

    private static final String STATE_SELECTED_OPTION = "STATE_SELECTED_OPTION";

    private Spinner spnOptions;
    private Toolbar toolbar;

    private int mSelectedOption = 0;
    private PhotoFragment mPhotoFragment;
    private InfoFragment mInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreInstanceState(savedInstanceState);
        initViews();
        loadFragments();
    }

    private void loadFragments() {
        mPhotoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag(
                TAG_PHOTO_FRAGMENT);
        if (mPhotoFragment == null) {
            mPhotoFragment = PhotoFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), R.id.flContent, mPhotoFragment,
                    TAG_PHOTO_FRAGMENT);
        }
        mInfoFragment = (InfoFragment) getSupportFragmentManager().findFragmentByTag(
                TAG_INFO_FRAGMENT);
        if (mInfoFragment == null) {
            mInfoFragment = InfoFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), R.id.flContent, mInfoFragment,
                    TAG_INFO_FRAGMENT);
        }
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSelectedOption = savedInstanceState.getInt(STATE_SELECTED_OPTION);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        spnOptions = findViewById(R.id.spnOptions);

        setupToolbar();
        spnOptions.setSelection(mSelectedOption);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            ToolbarSpinnerAdapter adaptador = new ToolbarSpinnerAdapter(
                    getSupportActionBar().getThemedContext(),
                    new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.options))));
            spnOptions.setOnItemSelectedListener(this);
            spnOptions.setAdapter(adaptador);
            // toolbar.addView(spnOptions);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_OPTION, spnOptions.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case PHOTO_OPTION_POSITION:
                showPhotoFragment();
                break;
            case INFO_OPTION_POSITION:
                showInfoFragment();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void showPhotoFragment() {
        getSupportFragmentManager().beginTransaction()
                .hide(mInfoFragment)
                .show(mPhotoFragment)
                .commit();
    }

    private void showInfoFragment() {
        getSupportFragmentManager().beginTransaction()
                .hide(mPhotoFragment)
                .show(mInfoFragment)
                .commit();
    }

}
