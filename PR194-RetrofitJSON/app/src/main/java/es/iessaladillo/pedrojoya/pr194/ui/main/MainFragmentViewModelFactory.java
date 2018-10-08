package es.iessaladillo.pedrojoya.pr194.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr194.data.remote.Api;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Api api;

    MainFragmentViewModelFactory(Api api) {
        this.api = api;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainFragmentViewModel(api);
    }

}
