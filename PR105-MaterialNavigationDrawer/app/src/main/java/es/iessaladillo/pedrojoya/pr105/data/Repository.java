package es.iessaladillo.pedrojoya.pr105.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    LiveData<List<Student>> queryStudents();

}
