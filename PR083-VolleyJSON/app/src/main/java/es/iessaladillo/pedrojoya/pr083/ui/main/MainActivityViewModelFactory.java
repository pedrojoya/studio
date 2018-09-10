package es.iessaladillo.pedrojoya.pr083.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final RequestQueue requestQueue;
    private final String urlString;

    public MainActivityViewModelFactory(RequestQueue requestQueue, String urlString) {
        this.requestQueue = requestQueue;
        this.urlString = urlString;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(requestQueue, urlString);
    }

}
