package es.iessaladillo.pedrojoya.pr021.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr021.data.local.Database;
import es.iessaladillo.pedrojoya.pr021.data.local.model.Country;

public class RepositoryImpl implements Repository {

    @NonNull private final Database database;

    public RepositoryImpl(@NonNull Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public List<Country> queryCountries() {
        return database.queryCountries();
    }

}
