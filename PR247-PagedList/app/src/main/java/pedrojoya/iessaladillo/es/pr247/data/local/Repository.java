package pedrojoya.iessaladillo.es.pr247.data.local;

import android.arch.paging.DataSource;

import java.util.List;

import pedrojoya.iessaladillo.es.pr247.data.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    DataSource.Factory<Integer, Student> queryPagedStudents();
    void addFakeStudent();
    void deleteStudent(int position);

}
