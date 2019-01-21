package es.iessaladillo.pedrojoya.pr082.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr082.data.Repository;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

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
            throw new IllegalArgumentException("Wrong viewModelClass");
        }
    }

}
