package es.iessaladillo.pedrojoya.pr212.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr212.data.model.Student;

public interface Repository {

    List<Student> getStudents();
    Student getStudent(long studentId);
    long addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(long studentId);
    void onDestroy();

}
