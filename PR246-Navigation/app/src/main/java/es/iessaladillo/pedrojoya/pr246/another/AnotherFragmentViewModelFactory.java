package es.iessaladillo.pedrojoya.pr246.another;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class AnotherFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final String initialName;

    AnotherFragmentViewModelFactory(@NonNull String initialName) {
        this.initialName = initialName;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AnotherFragmentViewModel.class)) {
            return (T) new AnotherFragmentViewModel(initialName);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
