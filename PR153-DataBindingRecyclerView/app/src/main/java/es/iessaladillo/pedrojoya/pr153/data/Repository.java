package es.iessaladillo.pedrojoya.pr153.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public interface Repository {

    LiveData<List<Student>> getStudents();
    LiveData<Student> getStudent(long studentId);
    long insertStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(Student student);

}
