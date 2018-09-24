package es.iessaladillo.pedrojoya.pr011.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr011.data.Repository;

class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
