package es.iessaladillo.pedrojoya.pr251.ui.student;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr251.data.Repository;

class StudentFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    StudentFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StudentFragmentViewModel(repository);
    }

}
