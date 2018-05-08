package es.iessaladillo.pedrojoya.pr153.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import es.iessaladillo.pedrojoya.pr153.data.Repository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;
    private final Resources resources;

    public MainActivityViewModelFactory(Repository repository, Resources resources) {
        this.repository = repository;
        this.resources = resources;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository, resources);
    }
}
