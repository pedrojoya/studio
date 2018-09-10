package es.iessaladillo.pedrojoya.pr195.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr195.data.Repository;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }

}
