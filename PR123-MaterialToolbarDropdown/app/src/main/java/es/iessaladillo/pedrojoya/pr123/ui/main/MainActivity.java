package es.iessaladillo.pedrojoya.pr123.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.base.ToolbarSpinnerAdapter;
import es.iessaladillo.pedrojoya.pr123.ui.info.InfoFragment;
import es.iessaladillo.pedrojoya.pr123.ui.photo.PhotoFragment;
import es.iessaladillo.pedrojoya.pr123.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final int PHOTO_OPTION_POSITION = 0;
    private static final int INFO_OPTION_POSITION = 1;

    private Spinner spnOptions;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        spnOptions = ActivityCompat.requireViewById(this, R.id.spnOptions);

        setupToolbar();
        // Load initial opition.
        //spnOptions.setSelection(viewModel.getSelectedOption());
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // Action bar can't show title because spinner is shown instead.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            ToolbarSpinnerAdapter adapter = new ToolbarSpinnerAdapter(
                    getSupportActionBar().getThemedContext(),
                    new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.options))));
            spnOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position,
                        long id) {
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
            });
            spnOptions.setAdapter(adapter);
            // toolbar.addView(spnOptions);
        }
    }

    private void showPhotoFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                PhotoFragment.newInstance(), PhotoFragment.class.getSimpleName());
    }

    private void showInfoFragment() {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                InfoFragment.newInstance(), InfoFragment.class.getSimpleName());
    }

}
