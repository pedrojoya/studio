package es.iessaladillo.pedrojoya.pr021.data;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr021.R;
import es.iessaladillo.pedrojoya.pr021.data.model.Country;

public class Database {

    private static Database instance;
    private final ArrayList<Country> countries;

    private Database() {
        countries = new ArrayList<>();
        // Initial data.
        countries.add(new Country(R.drawable.de, "Germay"));
        countries.add(new Country(R.drawable.dk, "Denmark"));
        countries.add(new Country(R.drawable.es, "Spain"));
        countries.add(new Country(R.drawable.fi, "Finland"));
        countries.add(new Country(R.drawable.fr, "France"));
        countries.add(new Country(R.drawable.gr, "Greece"));
        countries.add(new Country(R.drawable.nl, "Holand"));
        countries.add(new Country(R.drawable.ie, "Ireland"));
        countries.add(new Country(R.drawable.is, "Iceland"));
        countries.add(new Country(R.drawable.it, "Italy"));
        countries.add(new Country(R.drawable.lt, "Lithuania"));
        countries.add(new Country(R.drawable.no, "Norway"));
        countries.add(new Country(R.drawable.pl, "Poland"));
        countries.add(new Country(R.drawable.pt, "Portugal"));
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<Country> getCountries() {
        return countries;
    }

}
