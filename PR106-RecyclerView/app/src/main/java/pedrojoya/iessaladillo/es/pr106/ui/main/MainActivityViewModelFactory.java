package pedrojoya.iessaladillo.es.pr106.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr106.data.Repository;

class MainActivityViewModelFactory implements Factory {

    @NonNull
    private final Repository repository;

    MainActivityViewModelFactory(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
