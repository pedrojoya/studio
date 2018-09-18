package es.iessaladillo.pedrojoya.pr021.data.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr021.R;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

public class Database {

    private static volatile Database instance;

    private final ArrayList<Country> countries;

    private Database() {
        countries = new ArrayList<>(Arrays.asList(new Country(R.drawable.de, "Germay"),
                new Country(R.drawable.dk, "Denmark"), new Country(R.drawable.es, "Spain"),
                new Country(R.drawable.fi, "Finland"), new Country(R.drawable.fr, "France"),
                new Country(R.drawable.gr, "Greece"), new Country(R.drawable.nl, "Holand"),
                new Country(R.drawable.ie, "Ireland"), new Country(R.drawable.is, "Iceland"),
                new Country(R.drawable.it, "Italy"), new Country(R.drawable.lt, "Lithuania"),
                new Country(R.drawable.no, "Norway"), new Country(R.drawable.pl, "Poland"),
                new Country(R.drawable.pt, "Portugal")));
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public List<Country> queryCountries() {
        return countries;
    }

}
