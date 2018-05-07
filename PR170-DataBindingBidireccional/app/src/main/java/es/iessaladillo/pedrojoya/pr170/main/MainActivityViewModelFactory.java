package es.iessaladillo.pedrojoya.pr170.main;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

class MainActivityViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final String[] treatments;
    private final Application application;

    public MainActivityViewModelFactory(@NonNull Application application, String[] treatments) {
        super(application);
        this.application = application;
        this.treatments = treatments;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(application, treatments);
    }

}
