package es.iessaladillo.pedrojoya.pr057.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr057.data.local.Database;
import es.iessaladillo.pedrojoya.pr057.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public LiveData<List<Student>> queryStudents() {
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
