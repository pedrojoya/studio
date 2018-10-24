package pedrojoya.iessaladillo.es.pr225.data;

import java.util.List;

import androidx.annotation.NonNull;
import pedrojoya.iessaladillo.es.pr225.data.local.Database;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

public class RepositoryImpl implements Repository {

    @NonNull
    private final Database database;

    public RepositoryImpl(@NonNull Database database) {
        this.database = database;
    }

    @Override
    @NonNull
    public List<Student> queryStudents() {
        return database.queryStudents();
    }

}
