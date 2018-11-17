package es.iessaladillo.pedrojoya.pr018.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final int menuItemResIdOfInitialEffect;

    @SuppressWarnings("SameParameterValue")
    MainFragmentViewModelFactory(int menuItemResIdOfInitialEffect) {
        this.menuItemResIdOfInitialEffect = menuItemResIdOfInitialEffect;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainFragmentViewModel(menuItemResIdOfInitialEffect);
    }

}
