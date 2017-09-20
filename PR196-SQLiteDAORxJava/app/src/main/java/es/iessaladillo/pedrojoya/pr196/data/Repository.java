package es.iessaladillo.pedrojoya.pr196.data;

import java.util.List;

import es.iessaladillo.pedrojoya.pr196.data.model.Student;
import io.reactivex.Single;

public interface Repository {

    Single<List<Student>> getStudents();
    Single<Student> getStudent(long studentId);
    Single<Long> addStudent(Student student);
    Single<Integer> updateStudent(Student student);
    Single<Integer> deleteStudent(Student student);

}
