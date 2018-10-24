package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr011.data.local.Database;

public class RepositoryImpl implements Repository {

    @NonNull
    private final Database database;

    public RepositoryImpl(@NonNull Database database) {
        this.database = database;
    }

    @Override
    public List<String> queryStudents() {
        return database.getStudents();
    }

    @Override
    public void addStudent(@NonNull String student) {
        database.addStudent(student);
    }

    @Override
    public void deleteStudent(@NonNull String student) {
        database.deleteStudent(student);
    }

}
