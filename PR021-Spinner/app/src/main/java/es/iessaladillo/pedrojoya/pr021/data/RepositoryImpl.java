package es.iessaladillo.pedrojoya.pr021.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr021.data.model.Country;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<Country> getCountries() {
        return database.getCountries();
    }

}
