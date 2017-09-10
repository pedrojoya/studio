package es.iessaladillo.pedrojoya.pr211.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr211.data.model.Student;

public interface Repository {

    LiveData<List<Student>> getStudents();
    LiveData<Student> getStudent(long studentId);
    long addStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(Student student);

}
