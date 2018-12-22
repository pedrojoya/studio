package pedrojoya.iessaladillo.es.pr230.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import pedrojoya.iessaladillo.es.pr230.data.local.Database;
import pedrojoya.iessaladillo.es.pr230.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private final Database database;

    public RepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return database.queryStudents();
    }

}