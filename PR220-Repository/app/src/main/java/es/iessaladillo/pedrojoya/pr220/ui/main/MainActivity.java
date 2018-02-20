package es.iessaladillo.pedrojoya.pr220.ui.main;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.base.SingleFragmentActivity;
import es.iessaladillo.pedrojoya.pr220.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr220.databinding.ActivityMainBinding;

public class MainActivity extends SingleFragmentActivity<MainActivityViewModel,
        ActivityMainBinding> {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setMe(this);
    }

    @Override
    protected Class<MainActivityViewModel> getViewModelClass() {
        return MainActivityViewModel.class;
    }

    @Override
    protected MainActivityViewModel createViewModel(Application application) {
        return new MainActivityViewModel(RepositoryImpl.getInstance(this));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTag() {
        return TAG_MAIN_FRAGMENT;
    }

    @Override
    public Fragment getFragment() {
        return new MainFragment();
    }

    @Override
    protected int getContainerResId() {
        return R.id.flContent;
    }

}
