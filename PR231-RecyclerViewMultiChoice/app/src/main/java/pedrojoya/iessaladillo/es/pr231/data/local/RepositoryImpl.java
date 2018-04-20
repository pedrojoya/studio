package pedrojoya.iessaladillo.es.pr231.data.local;

import java.util.List;

import pedrojoya.iessaladillo.es.pr231.data.model.Student;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public synchronized static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<Student> getStudents() {
        return database.getStudents();
    }

    @Override
    public void addStudent() {
        database.addStudent(database.createStudent());
    }

    @Override
    public void deleteStudent(Student student) {
        database.deleteStudent(student);
    }

}
