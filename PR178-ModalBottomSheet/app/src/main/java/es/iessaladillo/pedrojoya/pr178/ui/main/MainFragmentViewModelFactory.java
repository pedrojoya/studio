package es.iessaladillo.pedrojoya.pr178.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import es.iessaladillo.pedrojoya.pr178.data.Repository;

class MainFragmentViewModelFactory implements Factory {

    private final Repository repository;

    MainFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(repository);
        } else {
            throw new IllegalArgumentException("Incorrect ViewModel class");
        }
    }

}
