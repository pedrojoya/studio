package es.iessaladillo.pedrojoya.pr251.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

@SuppressWarnings("UnusedReturnValue")
public interface Repository {

    LiveData<List<Student>> queryStudents();
    LiveData<Student> queryStudent(long studentId);
    void insertStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Student student);

}
