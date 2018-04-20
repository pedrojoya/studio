package es.iessaladillo.pedrojoya.pr049.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr049.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr049.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr049.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";
    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";

    private MainActivityViewModel mViewModel;
    private DetailFragment frgDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if (savedInstanceState != null) {
            mViewModel.onRestoreInstanceState(savedInstanceState);
        }
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flMain,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT);
        }
        if (ConfigurationUtils.hasLandscapeOrientation(this)) {
            frgDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentByTag(
                    TAG_DETAIL_FRAGMENT);
            if (frgDetailFragment == null) {
                frgDetailFragment = DetailFragment.newInstance(mViewModel.getSelectedItem());
                FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                        frgDetailFragment, TAG_MAIN_FRAGMENT);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModel.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(String item) {
        mViewModel.setSelectedItem(item);
        if (ConfigurationUtils.hasLandscapeOrientation(this)) {
            frgDetailFragment.setItem(item);
        } else {
            DetailActivity.start(this, item);
        }
    }

}