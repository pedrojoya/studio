package es.iessaladillo.pedrojoya.pr194.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr194.data.remote.Api;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Api api;

    public MainActivityViewModelFactory(Api api) {
        this.api = api;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(api);
    }

}
