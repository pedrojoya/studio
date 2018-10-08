package es.iessaladillo.pedrojoya.pr169.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr169.data.remote.Api;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Api api;

    public MainFragmentViewModelFactory(Api api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel(api);
    }

}
