package es.iessaladillo.pedrojoya.pr011.data;

import java.util.List;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final Database database;

    private RepositoryImpl(Database database) {
        this.database = database;
    }

    public static RepositoryImpl getInstance(Database database) {
        if (instance == null) {
            instance = new RepositoryImpl(database);
        }
        return instance;
    }

    @Override
    public List<String> getStudents() {
        return database.getStudents();
    }

    @Override
    public void addStudent(String student) {
        database.addStudent(student);
    }

    @Override
    public void deleteStudent(int position) {
        database.deleteStudent(position);
    }

}
