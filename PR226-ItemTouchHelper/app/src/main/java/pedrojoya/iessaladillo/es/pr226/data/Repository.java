package pedrojoya.iessaladillo.es.pr226.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> getStudents();
    void insertStudent(Student student);
    void deleteStudent(Student student);

}