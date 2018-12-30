package es.iessaladillo.pedrojoya.pr249.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr249.data.local.Database;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public LiveData<List<String>> queryStudents() {
        return database.queryStudents();
    }

}
