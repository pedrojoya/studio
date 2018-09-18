package es.iessaladillo.pedrojoya.pr012.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr012.data.local.Database;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Student> queryStudents() {
        return database.queryStudents();
    }

}
