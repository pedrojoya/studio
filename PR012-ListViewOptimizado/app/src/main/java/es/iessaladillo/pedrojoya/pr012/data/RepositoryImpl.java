package es.iessaladillo.pedrojoya.pr012.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr012.data.local.Database;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public List<Student> queryStudents() {
        return database.queryStudents();
    }

    @Override
    public void deleteStudent(@NonNull Student student) {
        database.deleteStudent(student);
    }

}
