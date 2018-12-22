package es.iessaladillo.pedrojoya.pr123.ui.main;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @IdRes private final int defaultEffectResId;

    public MainActivityViewModelFactory(int defaultEffectResId) {
        this.defaultEffectResId = defaultEffectResId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(defaultEffectResId);
    }

}
