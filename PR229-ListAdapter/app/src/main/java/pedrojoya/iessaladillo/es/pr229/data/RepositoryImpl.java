package pedrojoya.iessaladillo.es.pr229.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pedrojoya.iessaladillo.es.pr229.data.local.Database;
import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return database.queryStudents();
    }

    @Override
    public void addStudent(Student student) {
        database.addStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        database.deleteStudent(student);
    }

    @Override
    public void updateStudent(Student student, Student newStudent) {
        database.updateStudent(student, newStudent);
    }

}