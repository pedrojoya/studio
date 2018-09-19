package es.iessaladillo.pedrojoya.pr016.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr016.data.local.Database;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    public List<Level> queryLevels() {
        return database.queryLevels();
    }

    @Override
    public List<Student> queryStudentsByLevel(long levelId) {
        return database.queryStudentsByLevel(levelId);
    }

}
