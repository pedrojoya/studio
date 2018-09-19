package es.iessaladillo.pedrojoya.pr014.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> queryStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}
