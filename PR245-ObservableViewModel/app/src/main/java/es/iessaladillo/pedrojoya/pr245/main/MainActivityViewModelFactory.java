package es.iessaladillo.pedrojoya.pr245.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final String[] treatments;

    public MainActivityViewModelFactory(String[] treatments) {
        this.treatments = treatments;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(treatments);
    }

}
