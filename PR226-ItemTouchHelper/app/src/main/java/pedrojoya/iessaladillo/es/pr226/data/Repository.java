package pedrojoya.iessaladillo.es.pr226.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

public interface Repository {

    @NonNull
    LiveData<List<Student>> getStudents();
    void insertStudent(@NonNull Student student);
    void deleteStudent(@NonNull Student student);

}