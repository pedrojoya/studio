package es.iessaladillo.pedrojoya.pr132.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr132.data.local.Database;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<String> queryStudents() {
        return database.queryStudents();
    }

}
