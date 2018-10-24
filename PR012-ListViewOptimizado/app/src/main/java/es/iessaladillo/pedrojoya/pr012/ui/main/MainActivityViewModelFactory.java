package es.iessaladillo.pedrojoya.pr012.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr012.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Repository repository;

    public MainActivityViewModelFactory(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(repository);
    }
    
}
