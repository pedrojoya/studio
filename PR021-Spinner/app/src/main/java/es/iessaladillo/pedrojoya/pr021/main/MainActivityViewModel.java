package es.iessaladillo.pedrojoya.pr021.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr021.data.Repository;
import es.iessaladillo.pedrojoya.pr021.data.model.Country;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private ArrayList<Country> data;
    private final Repository repository;

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public ArrayList<Country> getData() {
        if (data == null) {
            data = (ArrayList<Country>) repository.getCountries();
        }
        return data;
    }

}
