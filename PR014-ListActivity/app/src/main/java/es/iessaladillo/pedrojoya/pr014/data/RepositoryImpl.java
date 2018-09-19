package es.iessaladillo.pedrojoya.pr014.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr014.data.local.Database;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<Student> queryStudents() {
        return database.queryStudents();
    }

    @Override
    public void insertStudent(Student student) {
        database.insertStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        database.deleteStudent(student);
    }

}
