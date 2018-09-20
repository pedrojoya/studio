package pedrojoya.iessaladillo.es.pr228.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pedrojoya.iessaladillo.es.pr228.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> queryStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}