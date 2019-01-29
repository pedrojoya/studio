package es.iessaladillo.pedrojoya.pr100.ui.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final List<String> initialStudents;

    MainFragmentViewModelFactory(List<String> initialStudents) {
        this.initialStudents = initialStudents;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(initialStudents);
        } else {
            throw  new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
