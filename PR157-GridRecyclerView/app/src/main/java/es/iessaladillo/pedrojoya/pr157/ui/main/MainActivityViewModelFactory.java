package es.iessaladillo.pedrojoya.pr157.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import es.iessaladillo.pedrojoya.pr157.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(repository);
    }
}
