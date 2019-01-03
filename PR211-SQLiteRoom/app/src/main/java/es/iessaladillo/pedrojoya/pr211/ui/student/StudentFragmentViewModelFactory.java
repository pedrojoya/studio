package es.iessaladillo.pedrojoya.pr211.ui.student;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr211.data.Repository;

class StudentFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    StudentFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StudentFragmentViewModel.class)) {
            return (T) new StudentFragmentViewModel(repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModelClass");
        }
    }

}
