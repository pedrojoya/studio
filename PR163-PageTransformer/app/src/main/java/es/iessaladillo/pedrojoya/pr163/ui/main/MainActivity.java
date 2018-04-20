package es.iessaladillo.pedrojoya.pr163.ui.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import es.iessaladillo.pedrojoya.pr163.R;
import es.iessaladillo.pedrojoya.pr163.transformers.CubeTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.DeepTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.DoorsTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.RotateTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.ScaleTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.TextTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.UpTransformer;
import es.iessaladillo.pedrojoya.pr163.transformers.VerticalTransformer;
import es.iessaladillo.pedrojoya.pr163.utils.ToolbarSpinnerAdapter;
import es.iessaladillo.pedrojoya.pr163.utils.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    private static final ViewPager.PageTransformer[] TRANSFORMATIONS = new ViewPager
            .PageTransformer[]{new UpTransformer(), new RotateTransformer(),
                               new ScaleTransformer(), new DoorsTransformer(),
                               new CubeTransformer(), new TextTransformer(),
                               new DeepTransformer(), new VerticalTransformer()};

    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        setupToolbar();
        setupViewPager();
        setupSpinner();
    }

    private void setupToolbar() {
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        // Spinner instead of title.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupViewPager() {
        mViewPager = ActivityCompat.requireViewById(this, R.id.viewPager);
        mViewPager.setAdapter(new MainActivityFragmentPagerAdapter(this, getSupportFragmentManager
                ()));
    }

    private void setupSpinner() {
        final Spinner spinner = ActivityCompat.requireViewById(this, R.id.spinner);
        if (spinner != null && getSupportActionBar() != null) {
            // Use theme context for ToolbarSpinnerAdapter.
            ToolbarSpinnerAdapter adapter = new ToolbarSpinnerAdapter(
                    getSupportActionBar().getThemedContext(), new ArrayList<>(
                    Arrays.asList(getResources().getStringArray(R.array.transformations))));
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> parent, final View view,
                        final int position, final long id) {
                    if (position == TRANSFORMATIONS.length - 1) {
                        mViewPager.setSwipeOrientation(VerticalViewPager.VERTICAL);
                    } else {
                        mViewPager.setSwipeOrientation(VerticalViewPager.HORIZONTAL);
                        mViewPager.setPageTransformer(true, TRANSFORMATIONS[position]);
                    }
                }

                @Override
                public void onNothingSelected(final AdapterView<?> parent) {
                    mViewPager.setPageTransformer(true, TRANSFORMATIONS[0]);
                }
            });
        }
    }

}
