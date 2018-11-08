package pedrojoya.iessaladillo.es.pr201.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr201.data.Repository;

final class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Repository repository;

    MainActivityViewModelFactory(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(repository);
    }

}
