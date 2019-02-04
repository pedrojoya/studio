package pedrojoya.iessaladillo.es.pr256.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    LiveData<PagedList<Student>> queryAllStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}
