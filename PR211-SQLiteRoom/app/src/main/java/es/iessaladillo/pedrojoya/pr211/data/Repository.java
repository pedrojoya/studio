package es.iessaladillo.pedrojoya.pr211.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr211.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> queryStudents();
    LiveData<Student> queryStudent(long studentId);
    void insertStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Student student);

}
