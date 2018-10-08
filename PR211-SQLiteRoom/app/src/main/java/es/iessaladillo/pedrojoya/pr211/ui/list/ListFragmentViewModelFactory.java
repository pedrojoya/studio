package es.iessaladillo.pedrojoya.pr211.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr211.data.Repository;
import es.iessaladillo.pedrojoya.pr211.ui.main.MainActivityViewModel;

class ListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;
    private final MainActivityViewModel activityViewModel;

    ListFragmentViewModelFactory(Repository repository, MainActivityViewModel activityViewModel) {
        this.repository = repository;
        this.activityViewModel = activityViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ListFragmentViewModel(repository, activityViewModel);
    }

}
