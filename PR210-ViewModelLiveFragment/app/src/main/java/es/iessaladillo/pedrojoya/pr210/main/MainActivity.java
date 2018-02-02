package es.iessaladillo.pedrojoya.pr210.main;

import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr210.R;
import es.iessaladillo.pedrojoya.pr210.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr210.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr210.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr210.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";
    private static final String TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT";

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
        mViewModel.getCurrentItem().observe(this, item -> {
            if (ConfigurationUtils.hasLandscapeOrientation(this)) {
                showDetailFragment();
            } else {
                DetailActivity.start(this, item);
            }
        });
    }

    private void showDetailFragment() {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flDetail,
                DetailFragment.newInstance(), TAG_DETAIL_FRAGMENT, TAG_DETAIL_FRAGMENT,
                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    @Override
    public void onBackPressed() {
        if (!ConfigurationUtils.hasLandscapeOrientation(this)) {
            // No backstack.
            getSupportFragmentManager().popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        super.onBackPressed();
    }

}