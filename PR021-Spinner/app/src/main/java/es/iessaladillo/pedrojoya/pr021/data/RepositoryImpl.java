package es.iessaladillo.pedrojoya.pr021.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr021.data.local.Database;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Country> queryCountries() {
        return database.queryCountries();
    }

}
