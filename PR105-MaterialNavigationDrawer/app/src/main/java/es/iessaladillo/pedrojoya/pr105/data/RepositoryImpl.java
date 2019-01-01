package es.iessaladillo.pedrojoya.pr105.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr105.data.local.Database;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

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
