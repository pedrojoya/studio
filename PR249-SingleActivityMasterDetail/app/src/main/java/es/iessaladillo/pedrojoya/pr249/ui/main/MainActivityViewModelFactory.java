package es.iessaladillo.pedrojoya.pr249.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr249.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

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
