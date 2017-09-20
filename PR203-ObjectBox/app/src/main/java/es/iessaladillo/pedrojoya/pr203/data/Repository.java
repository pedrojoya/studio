package es.iessaladillo.pedrojoya.pr203.data;

import es.iessaladillo.pedrojoya.pr203.data.model.Student;
import io.objectbox.query.Query;

public interface Repository {

    Query<Student> queryStudents();

    void deleteStudent(Student student);

    Student queryStudent(long studentId);

    void saveStudent(Student student);

}
