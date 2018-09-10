package pedrojoya.iessaladillo.es.pr201.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr201.data.Repository;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public MainActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(repository);
    }

}
