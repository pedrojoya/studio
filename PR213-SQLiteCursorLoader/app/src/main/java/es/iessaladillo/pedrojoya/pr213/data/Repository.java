package es.iessaladillo.pedrojoya.pr213.data;

import android.database.Cursor;

import es.iessaladillo.pedrojoya.pr213.data.model.Student;

public interface Repository {

    Cursor queryStudents();
    Student getStudent(long studentId);
    long addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(long studentId);
    void onDestroy();

}
