package pedrojoya.iessaladillo.es.pr106.data;

import java.util.List;

import androidx.annotation.NonNull;
import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<Student> queryStudents();
    void insertStudent(@NonNull Student student);
    void deleteStudent(@NonNull Student student);

}
