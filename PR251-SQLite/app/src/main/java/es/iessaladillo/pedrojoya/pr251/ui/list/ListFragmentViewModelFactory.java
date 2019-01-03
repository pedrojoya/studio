package es.iessaladillo.pedrojoya.pr251.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr251.data.Repository;

class ListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    ListFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ListFragmentViewModel(repository);
    }

}
