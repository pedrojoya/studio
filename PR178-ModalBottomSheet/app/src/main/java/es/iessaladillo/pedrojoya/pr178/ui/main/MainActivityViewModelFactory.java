package es.iessaladillo.pedrojoya.pr178.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr178.data.local.Database;
import es.iessaladillo.pedrojoya.pr178.data.local.RepositoryImpl;

class MainActivityViewModelFactory implements Factory {
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(RepositoryImpl.getInstance(Database.getInstance()));
    }
}
