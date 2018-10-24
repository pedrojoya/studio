package es.iessaladillo.pedrojoya.pr014.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr014.data.local.Database;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

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
    public void insertStudent(@NonNull Student student) {
        database.insertStudent(student);
    }

    @Override
    public void deleteStudent(@NonNull Student student) {
        database.deleteStudent(student);
    }

}
