package pedrojoya.iessaladillo.es.pr228.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr228.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addFakeStudent();
    void deleteStudent(int position);

}
