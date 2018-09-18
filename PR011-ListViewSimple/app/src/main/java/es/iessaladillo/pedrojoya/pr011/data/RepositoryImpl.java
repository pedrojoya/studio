package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr011.data.local.Database;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<String> queryStudents() {
        return database.getStudents();
    }

    @Override
    public void addStudent(String student) {
        database.addStudent(student);
    }

    @Override
    public void deleteStudent(String student) {
        database.deleteStudent(student);
    }

}
