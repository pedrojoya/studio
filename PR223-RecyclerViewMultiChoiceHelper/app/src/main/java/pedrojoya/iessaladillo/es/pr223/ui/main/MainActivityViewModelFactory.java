package pedrojoya.iessaladillo.es.pr223.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;

import pedrojoya.iessaladillo.es.pr223.data.local.Database;
import pedrojoya.iessaladillo.es.pr223.data.local.RepositoryImpl;

class MainActivityViewModelFactory implements Factory {
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(RepositoryImpl.getInstance(Database.getInstance()));
    }
}
