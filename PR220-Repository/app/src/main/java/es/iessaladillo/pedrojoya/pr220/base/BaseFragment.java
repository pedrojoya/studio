package es.iessaladillo.pedrojoya.pr220.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activitystarter.ActivityStarter;

@SuppressWarnings({"unchecked", "unused"})
public abstract class BaseFragment<T extends ViewModel, R extends ViewDataBinding> extends Fragment {

    protected R binding;
    protected T viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStarter.fill(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ActivityStarter.save(this, outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <V extends ViewModel> V create(@NonNull Class<V> modelClass) {
                return (V) createViewModel();
            }
        }).get(getViewModelClass());
    }

    protected abstract Class<T> getViewModelClass();

    protected abstract T createViewModel();

    @LayoutRes protected abstract int getLayoutResId();

}
