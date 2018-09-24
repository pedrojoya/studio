package es.iessaladillo.pedrojoya.pr049.ui.main;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr049.data.local.Database;
import es.iessaladillo.pedrojoya.pr049.ui.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr049.ui.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr049.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr049.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private MainActivityViewModel viewModel;
    private DetailFragment frgDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()),
                        getString(R.string.main_activity_no_item))).get(
                MainActivityViewModel.class);
        if (savedInstanceState != null) {
            viewModel.onRestoreInstanceState(savedInstanceState);
        }
        if (getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName())
                == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flMain,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
        if (ConfigurationUtils.inLandscape(this)) {
            //            frgDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentByTag(
            //                    TAG_DETAIL_FRAGMENT);
            //            if (frgDetailFragment == null) {
            frgDetailFragment = DetailFragment.newInstance(viewModel.getSelectedItem());
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                    frgDetailFragment, DetailFragment.class.getSimpleName());
            //            }
        }
        if (ConfigurationUtils.inLandscape(this) && !viewModel.getSelectedItem().equals(
                getString(R.string.main_activity_no_item))) {
            DetailActivity.start(this, viewModel.getSelectedItem());
        }
    }

    // We need to save elements form the viewModel because it will be
    // destroyed when configuration change occurs in the detail activity.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(String item) {
        viewModel.setSelectedItem(item);
        if (ConfigurationUtils.inLandscape(this)) {
            frgDetailFragment.setItem(item);
        } else {
            DetailActivity.start(this, item);
        }
    }

}