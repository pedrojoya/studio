package pedrojoya.iessaladillo.es.pr228.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr228.data.Repository;

class MainActivityViewModelFactory implements Factory {

    private final Repository repository;

    MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
