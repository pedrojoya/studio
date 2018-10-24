package es.iessaladillo.pedrojoya.pr016.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr016.data.local.Database;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Level;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;

public class RepositoryImpl implements Repository {

    @NonNull
    private final Database database;

    public RepositoryImpl(@NonNull Database database) {
        this.database = database;
    }

    @NonNull
    public List<Level> queryLevels() {
        return database.queryLevels();
    }

    @Override
    @NonNull
    public List<Student> queryStudentsByLevel(long levelId) {
        return database.queryStudentsByLevel(levelId);
    }

}
