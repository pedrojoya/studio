package es.iessaladillo.pedrojoya.pr011;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import es.iessaladillo.pedrojoya.pr011.data.Repository;

class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
