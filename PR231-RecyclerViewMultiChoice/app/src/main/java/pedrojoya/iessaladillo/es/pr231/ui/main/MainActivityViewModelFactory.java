package pedrojoya.iessaladillo.es.pr231.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.support.annotation.NonNull;

import pedrojoya.iessaladillo.es.pr231.data.RepositoryImpl;
import pedrojoya.iessaladillo.es.pr231.data.local.Database;

public class MainActivityViewModelFactory implements Factory {
    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(RepositoryImpl.getInstance(Database.getInstance()));
    }
}
