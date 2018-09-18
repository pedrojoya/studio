package es.iessaladillo.pedrojoya.pr021.main;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import es.iessaladillo.pedrojoya.pr021.data.Repository;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Country> countries;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Country> getCountries() {
        if (countries == null) {
            countries = repository.queryCountries();
        }
        return countries;
    }

}
