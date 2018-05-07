package es.iessaladillo.pedrojoya.pr244.main;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.support.annotation.NonNull;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Resources resources;

    public MainActivityViewModelFactory(@NonNull Resources resources) {
        this.resources = resources;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(resources);
    }

}
