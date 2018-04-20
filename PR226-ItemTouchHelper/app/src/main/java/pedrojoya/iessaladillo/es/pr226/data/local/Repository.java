package pedrojoya.iessaladillo.es.pr226.data.local;

import java.util.List;

import pedrojoya.iessaladillo.es.pr226.data.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addFakeStudent();
    void deleteStudent(int position);

}
