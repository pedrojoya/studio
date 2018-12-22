package pedrojoya.iessaladillo.es.pr230.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import pedrojoya.iessaladillo.es.pr230.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> queryStudents();

}