package es.iessaladillo.pedrojoya.pr082.ui.main;

import com.android.volley.RequestQueue;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final RequestQueue requestQueue;

    public MainFragmentViewModelFactory(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainFragmentViewModel(requestQueue);
    }
}
