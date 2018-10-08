package es.iessaladillo.pedrojoya.pr251.ui.student;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr251.data.Repository;
import es.iessaladillo.pedrojoya.pr251.ui.main.MainActivityViewModel;

class StudentFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;
    private final MainActivityViewModel activityViewModel;

    public StudentFragmentViewModelFactory(Repository repository,
            MainActivityViewModel activityViewModel) {
        this.repository = repository;
        this.activityViewModel = activityViewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new StudentFragmentViewModel(repository, activityViewModel);
    }

}
