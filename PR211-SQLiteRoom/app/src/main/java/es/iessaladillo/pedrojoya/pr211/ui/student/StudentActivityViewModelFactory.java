package es.iessaladillo.pedrojoya.pr211.ui.student;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import es.iessaladillo.pedrojoya.pr211.data.Repository;

class StudentActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository repository;

    public StudentActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new StudentActivityViewModel(repository);
    }
}