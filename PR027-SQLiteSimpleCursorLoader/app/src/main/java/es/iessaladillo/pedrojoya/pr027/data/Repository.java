package es.iessaladillo.pedrojoya.pr027.data;

import android.database.Cursor;

import es.iessaladillo.pedrojoya.pr027.data.model.Student;

public interface Repository {

    Cursor queryStudents();
    Student getStudent(long studentId);
    long addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(long studentId);
    void onDestroy();

}
