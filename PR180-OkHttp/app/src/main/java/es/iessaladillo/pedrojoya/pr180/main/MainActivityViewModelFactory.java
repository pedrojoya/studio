package es.iessaladillo.pedrojoya.pr180.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final OkHttpClient okHttpClient;

    public MainActivityViewModelFactory(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(okHttpClient);
    }

}
