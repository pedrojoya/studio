package es.iessaladillo.pedrojoya.pr050.ui.photo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class PhotoFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final int defaultEffectId;

    @SuppressWarnings("SameParameterValue")
    PhotoFragmentViewModelFactory(int defaultEffectId) {
        this.defaultEffectId = defaultEffectId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotoFragmentViewModel(defaultEffectId);
    }

}
