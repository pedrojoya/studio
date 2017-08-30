package es.iessaladillo.pedrojoya.pr014.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr014.data.model.Student;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl() {
        this.database = Database.getInstance();
    }

    public synchronized static RepositoryImpl getInstance() {
        if (instance == null) {
            instance = new RepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Student> getStudents() {
        return database.getStudents();
    }

    @Override
    public void addStudent(Student student) {
        database.addStudent(student);
    }

    @Override
    public void deleteStudent(int position) {
        database.deleteStudent(position);
    }

}
