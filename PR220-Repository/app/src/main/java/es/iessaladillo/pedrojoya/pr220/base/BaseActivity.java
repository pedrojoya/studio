package es.iessaladillo.pedrojoya.pr220.base;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import activitystarter.ActivityStarter;
import activitystarter.MakeActivityStarter;

@SuppressWarnings("unchecked")
@MakeActivityStarter
public abstract class BaseActivity<T extends ViewModel, R extends ViewDataBinding> extends AppCompatActivity {

    protected R binding;
    @SuppressWarnings({"WeakerAccess", "unused"})
    protected T viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStarter.fill(this, savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        viewModel = ViewModelProviders.of(this, new AndoridViewModelFactory(getApplication())).get(
                getViewModelClass());
    }

    protected abstract Class<T> getViewModelClass();

    protected abstract T createViewModel(Application application);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ActivityStarter.save(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    private class AndoridViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

        private final Application application;

        @NonNull
        @Override
        public <V extends ViewModel> V create(@NonNull Class<V> modelClass) {
            return (V) createViewModel(application);
        }

        public AndoridViewModelFactory(@NonNull Application application) {
            super(application);
            this.application = application;
        }
    }

}
