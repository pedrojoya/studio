package es.iessaladillo.pedrojoya.pr165.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;

import es.iessaladillo.pedrojoya.pr165.data.local.Database;
import es.iessaladillo.pedrojoya.pr165.data.local.RepositoryImpl;

class MainActivityViewModelFactory implements Factory {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(RepositoryImpl.getInstance(Database.getInstance()));
    }
}
