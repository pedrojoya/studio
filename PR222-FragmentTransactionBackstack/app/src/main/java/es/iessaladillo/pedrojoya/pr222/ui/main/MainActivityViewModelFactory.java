package es.iessaladillo.pedrojoya.pr222.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr222.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final String defaultItem;
    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository, String defaultItem) {
        this.repository = repository;
        this.defaultItem = defaultItem;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository, defaultItem);
    }
}
