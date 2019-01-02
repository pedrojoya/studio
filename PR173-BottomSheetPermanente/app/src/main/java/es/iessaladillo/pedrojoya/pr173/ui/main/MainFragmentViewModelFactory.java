package es.iessaladillo.pedrojoya.pr173.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final int bsbInitialState;

    @SuppressWarnings("SameParameterValue")
    MainFragmentViewModelFactory(int bsbInitialState) {
        this.bsbInitialState = bsbInitialState;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(bsbInitialState);
        }
        throw new IllegalArgumentException(
                "Cannot instantiate ViewModel class with these arguments");
    }

}
