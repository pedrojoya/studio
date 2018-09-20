package pedrojoya.iessaladillo.es.pr106.data;

import java.util.List;

import pedrojoya.iessaladillo.es.pr106.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> queryStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}
