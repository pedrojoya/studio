package pedrojoya.iessaladillo.es.pr106.data.local;

import java.util.List;

import pedrojoya.iessaladillo.es.pr106.data.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addFakeStudent();
    void deleteStudent(int position);

}
