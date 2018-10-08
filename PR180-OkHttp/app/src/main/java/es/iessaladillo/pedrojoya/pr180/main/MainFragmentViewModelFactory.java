package es.iessaladillo.pedrojoya.pr180.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final OkHttpClient okHttpClient;

    public MainFragmentViewModelFactory(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmenViewModel(okHttpClient);
    }

}
