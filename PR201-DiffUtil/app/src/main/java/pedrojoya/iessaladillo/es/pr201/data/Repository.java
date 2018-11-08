package pedrojoya.iessaladillo.es.pr201.data;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public interface Repository {

    @NonNull LiveData<List<Student>> queryStudents(boolean desc);

    void insertStudent(@NonNull Student student);

    void deleteStudent(@NonNull Student student);

    void updateStudent(@NonNull Student student, @NonNull Student newStudent);

}
