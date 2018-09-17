package pedrojoya.iessaladillo.es.pr106.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addStudent(Student student);
    void deleteStudent(int position);

}
