package pedrojoya.iessaladillo.es.pr225.data;

import java.util.List;

import androidx.annotation.NonNull;
import pedrojoya.iessaladillo.es.pr225.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    @NonNull
    List<Student> queryStudents();

}
