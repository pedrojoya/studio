package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr210.R;
import es.iessaladillo.pedrojoya.pr210.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr210.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr210.detail.DetailFragmentBaseActivity;
import es.iessaladillo.pedrojoya.pr210.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr210.utils.FragmentUtils;

public class MainActivity extends DetailFragmentBaseActivity<MainActivityViewModel> {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";
    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";

    @SuppressWarnings("FieldCanBeLocal")
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        // This is the correct order to load the fragments.
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flMain,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT);
        }
        if (ConfigurationUtils.hasLandscapeOrientation(this)
                && getSupportFragmentManager().findFragmentByTag(TAG_DETAIL_FRAGMENT) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                    DetailFragment.newInstance(), TAG_MAIN_FRAGMENT);
        }
        //
        mViewModel.getCurrentItem().observe(this, item -> {
            if (!ConfigurationUtils.hasLandscapeOrientation(this)) {
                DetailActivity.start(this, item);
            }
        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModelClass() {
        return MainActivityViewModel.class;
    }

}