package es.iessaladillo.pedrojoya.pr169.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr169.data.remote.Api;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Api api;

    public MainActivityViewModelFactory(Api api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(api);
    }

}
