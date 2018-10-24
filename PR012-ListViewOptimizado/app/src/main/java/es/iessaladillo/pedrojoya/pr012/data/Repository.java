package es.iessaladillo.pedrojoya.pr012.data;

import java.util.List;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<Student> queryStudents();

    void deleteStudent(@NonNull Student student);

}
