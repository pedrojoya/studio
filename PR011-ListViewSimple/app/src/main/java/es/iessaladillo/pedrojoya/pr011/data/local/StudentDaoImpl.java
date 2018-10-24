package es.iessaladillo.pedrojoya.pr011.data.local;

import java.util.List;

import androidx.annotation.NonNull;

public class StudentDaoImpl implements StudentDao {

    private final Database database;

    public StudentDaoImpl(Database database) {
        this.database = database;
    }

    @Override
    public List<String> queryStudents() {
        return database.selectAll(Database.TABLE_STUDENTS);
    }

    @Override
    public void addStudent(@NonNull String student) {
        database.insert(Database.TABLE_STUDENTS, student);
    }

    @Override
    public void deleteStudent(@NonNull String student) {
        database.delete(Database.TABLE_STUDENTS, student);
    }

}
