package es.iessaladillo.pedrojoya.pr014.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr014.data.model.Student;

@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    List<Student> getStudents();
    void addStudent(Student student);
    void deleteStudent(int position);

}
