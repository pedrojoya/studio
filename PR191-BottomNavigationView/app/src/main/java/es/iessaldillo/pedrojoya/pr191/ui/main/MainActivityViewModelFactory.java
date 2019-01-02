package es.iessaldillo.pedrojoya.pr191.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private static final MainActivityViewModelFactory instance = new MainActivityViewModelFactory();

    static MainActivityViewModelFactory getInstance() {
        return instance;
    }

    private MainActivityViewModelFactory() { }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }

}
