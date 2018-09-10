package pedrojoya.iessaladillo.es.pr243.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr243.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addStudent();
    void deleteStudent(Student student);

}
