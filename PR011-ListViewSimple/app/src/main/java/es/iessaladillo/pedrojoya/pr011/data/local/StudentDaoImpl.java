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
    public int addStudent(@NonNull String student) {
        return database.insert(Database.TABLE_STUDENTS, student);
    }

    @Override
    public int deleteStudent(@NonNull String student) {
        return database.delete(Database.TABLE_STUDENTS, student);
    }

}
