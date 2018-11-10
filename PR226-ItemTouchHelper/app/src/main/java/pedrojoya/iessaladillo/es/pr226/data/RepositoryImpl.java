package pedrojoya.iessaladillo.es.pr226.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import pedrojoya.iessaladillo.es.pr226.data.local.Database;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

public class RepositoryImpl implements Repository {

    @NonNull
    private final Database database;

    public RepositoryImpl(@NonNull Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public LiveData<List<Student>> getStudents() {
        return database.queryStudentsSortedByOrder();
    }

    @Override
    public void insertStudent(@NonNull Student student) {
        database.insertStudent(student);
    }

    @Override
    public void deleteStudent(@NonNull Student student) {
        database.deleteStudent(student);
    }

    @Override
    public void updateStudents(@NonNull List<Student> students) {
        database.updateStudentsSettingOrder(students);
    }

}
