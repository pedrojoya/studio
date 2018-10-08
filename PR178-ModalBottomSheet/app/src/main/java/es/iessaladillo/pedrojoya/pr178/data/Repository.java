package es.iessaladillo.pedrojoya.pr178.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr178.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> getStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}