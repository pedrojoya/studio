package es.iessaladillo.pedrojoya.pr222.ui.main;

import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr222.R;
import es.iessaladillo.pedrojoya.pr222.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr222.data.local.Database;
import es.iessaladillo.pedrojoya.pr222.ui.detail.DetailActivity;
import es.iessaladillo.pedrojoya.pr222.ui.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr222.utils.ConfigurationUtils;
import es.iessaladillo.pedrojoya.pr222.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback, DetailFragment.Callback {

    private MainActivityViewModel viewModel;
    private boolean fromBackStack;

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
        // This is the correct order to load the fragments.
        // We always load both fragments, event in portrait (to easily recover if
        // orientation change happens in detail activity.
        if (getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName())
                == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flMain,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
        if (getSupportFragmentManager().findFragmentByTag(DetailFragment.class.getSimpleName())
                == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flDetail,
                    DetailFragment.newInstance(viewModel.getSelectedItem(),
                            viewModel.getSelectedItemKey()), DetailFragment.class.getSimpleName());
        }
    }

    @Override
    public void onItemSelected(String item, long key) {
        viewModel.setSelectedItemKey(key);
        viewModel.setSelectedItem(item);
        DetailFragment frgDetail = (DetailFragment) getSupportFragmentManager().findFragmentByTag(
                DetailFragment.class.getSimpleName());
        // We only load the new detail fragment if is different from the current one.
        if (frgDetail != null && viewModel.getSelectedItemKey() != frgDetail.getKey()) {
            // We load it even if in portrait mode, so that if an orientation change occurs on
            // detail activity the correct detail fragment is shown.
            showDetailFragment(item, key);
        }
        // We only show detail activity if we are in portrait.
        if (!ConfigurationUtils.inLandscape(this)) {
            DetailActivity.start(this, item, key);
        }
    }

    private void showDetailFragment(String item, long key) {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(), R.id.flDetail,
                DetailFragment.newInstance(item, key), DetailFragment.class.getSimpleName(), item,
                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    // When detail shown (even from backstack).
    @Override
    public void onDetailShown(String item, long key) {
        viewModel.setSelectedItemKey(key);
        viewModel.setSelectedItem(item);
        // If we come from backstack, se update selected item in main fragment.
        if (fromBackStack) {
            MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(
                R.id.flMain);
            if (mainFragment != null) {
                mainFragment.selectItem(key);
            }
        }
        fromBackStack = false;
    }

    @Override
    public void onBackPressed() {
        // We indicate we come from back stack.
        fromBackStack = true;
        if (!ConfigurationUtils.inLandscape(this)) {
            // No backstack.
            getSupportFragmentManager().popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        super.onBackPressed();
    }

    // We need to save elements form the viewModel because it will be
    // destroyed when configuration change occurs in the detail activity.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.onSaveInstanceState(outState);
    }

}