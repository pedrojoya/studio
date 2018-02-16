package es.iessaldillo.pedrojoya.pr208.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import es.iessaldillo.pedrojoya.pr208.data.Repository;
import es.iessaldillo.pedrojoya.pr208.data.RepositoryImpl;
import io.reactivex.Maybe;

public class MainActivityViewModel extends AndroidViewModel {

    private final Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositoryImpl(application.getApplicationContext());
    }

    public Maybe<String> searchPeople(String search) {
        return repository.searchPeople(search);
    }

    public Maybe<String> searchCharacter(String search) {
        return repository.searchCharacter(search);
    }

}
