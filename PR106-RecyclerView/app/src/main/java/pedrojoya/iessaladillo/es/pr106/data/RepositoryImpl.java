package pedrojoya.iessaladillo.es.pr106.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr106.data.local.Database;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

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
