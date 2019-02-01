package es.iessaladillo.pedrojoya.pr251.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr251.base.Resource;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

@SuppressWarnings("UnusedReturnValue")
public interface Repository {

    LiveData<List<Student>> queryStudents();
    LiveData<Student> queryStudent(long studentId);
    LiveData<Resource<Long>> insertStudent(Student student);
    LiveData<Resource<Integer>> updateStudent(Student student);
    LiveData<Resource<Integer>> deleteStudent(Student student);

}
