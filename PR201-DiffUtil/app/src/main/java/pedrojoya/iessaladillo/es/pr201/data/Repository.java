package pedrojoya.iessaladillo.es.pr201.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> queryStudents();

    void addStudent(Student student);

    void deleteStudent(Student student);

    void updateStudent(Student student, Student newStudent);

}
