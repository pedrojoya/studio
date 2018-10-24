package es.iessaladillo.pedrojoya.pr021.main;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr021.data.Repository;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private List<Country> countries;
    @NonNull private final Repository repository;

    public MainActivityViewModel(@NonNull Repository repository) {
        this.repository = repository;
    }

    @NonNull
    public List<Country> getCountries() {
        if (countries == null) {
            countries = repository.queryCountries();
        }
        return countries;
    }

}
