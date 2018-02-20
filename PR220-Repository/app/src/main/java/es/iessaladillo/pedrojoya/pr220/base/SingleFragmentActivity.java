package es.iessaladillo.pedrojoya.pr220.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import activitystarter.MakeActivityStarter;
import es.iessaladillo.pedrojoya.pr220.utils.FragmentUtils;

@MakeActivityStarter
public abstract class SingleFragmentActivity<T extends ViewModel, R extends ViewDataBinding>
        extends BaseActivity<T, R> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment();
    }

    private  void loadFragment() {
        if (getSupportFragmentManager().findFragmentByTag(getTag()) == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), getContainerResId(),
                    getFragment(), getTag());
        }
    }

    protected abstract String getTag();
    protected abstract Fragment getFragment();
    @SuppressWarnings("SameReturnValue")
    @IdRes protected abstract int getContainerResId();

}
